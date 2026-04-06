import { useState, useEffect, useCallback } from 'react';
import Navbar from '../components/Navbar';
import LeaderboardTable from '../components/LeaderboardTable';
import CreateTeamModal from '../components/CreateTeamModal';
import ConfirmDialog from '../components/ConfirmDialog';
import { getTeams, createTeam, deleteTeam, saveScore } from '../services/api';
import { Search, Plus } from 'lucide-react';

const DashboardPage = () => {
  const [teams, setTeams] = useState([]);
  const [filteredTeams, setFilteredTeams] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchQuery, setSearchQuery] = useState('');
  
  // Modal states
  const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);
  const [isCreating, setIsCreating] = useState(false);
  
  const [confirmDeleteState, setConfirmDeleteState] = useState({ isOpen: false, teamId: null });
  const [isDeleting, setIsDeleting] = useState(false);

  // Feedback states
  const [message, setMessage] = useState(null);

  const fetchTeams = useCallback(async () => {
    setLoading(true);
    try {
      // The backend will return a structure matching LeaderboardResponse.
      // Expected: data.data is an array if wrapped in ApiResponse
      const data = await getTeams();
      // Ensure we set the array
      const teamList = data.data || data; 
      setTeams(teamList);
      setFilteredTeams(teamList);
    } catch (error) {
      showMessage('Failed to fetch teams.', 'error');
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchTeams();
  }, [fetchTeams]);

  // Debounce search locally
  useEffect(() => {
    const timer = setTimeout(() => {
      if (!searchQuery.trim()) {
        setFilteredTeams(teams);
      } else {
        const lowerQ = searchQuery.toLowerCase();
        setFilteredTeams(teams.filter(t => 
          t.teamName && t.teamName.toLowerCase().includes(lowerQ)
        ));
      }
    }, 300);

    return () => clearTimeout(timer);
  }, [searchQuery, teams]);

  const showMessage = (msg, type = 'success') => {
    setMessage({ text: msg, type });
    setTimeout(() => setMessage(null), 3000);
  };

  const handleCreateTeam = async (teamData, onSuccess) => {
    setIsCreating(true);
    try {
      await createTeam(teamData);
      showMessage('Team created successfully!');
      fetchTeams();
      onSuccess();
    } catch (error) {
      showMessage('Failed to create team.', 'error');
    } finally {
      setIsCreating(false);
    }
  };

  const handleDeleteConfirm = async () => {
    if (!confirmDeleteState.teamId) return;
    
    setIsDeleting(true);
    try {
      await deleteTeam(confirmDeleteState.teamId);
      showMessage('Team deleted successfully!');
      fetchTeams();
    } catch (error) {
      showMessage('Failed to delete team.', 'error');
    } finally {
      setIsDeleting(false);
      setConfirmDeleteState({ isOpen: false, teamId: null });
    }
  };

  const handleSaveScore = async (teamId, scoreData) => {
    try {
      await saveScore(teamId, scoreData);
      showMessage('Score updated successfully!');
      fetchTeams();
    } catch (error) {
      showMessage('Failed to save score.', 'error');
    }
  };

  return (
    <div className="min-h-screen flex flex-col">
      <Navbar />
      
      <main className="flex-1 max-w-7xl w-full mx-auto px-4 sm:px-6 lg:px-8 py-8">
        
        {message && (
          <div className={`mb-4 p-4 rounded-md shadow-sm fixed top-4 right-4 z-50 ${message.type === 'error' ? 'bg-red-50 text-danger border border-red-200' : 'bg-green-50 text-success border border-green-200'}`}>
            {message.text}
          </div>
        )}

        <div className="flex flex-col md:flex-row md:items-center justify-between mb-6 gap-4">
          <div>
            <h2 className="text-2xl font-bold leading-7 text-gray-900 sm:truncate">Leaderboard</h2>
          </div>
          <div className="flex flex-col md:flex-row gap-3">
            <div className="relative">
              <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <Search className="h-5 w-5 text-gray-400" />
              </div>
              <input
                type="text"
                placeholder="Search team..."
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="pl-10 pr-3 py-2 border border-gray-300 rounded-md leading-5 bg-white placeholder-gray-500 focus:outline-none focus:placeholder-gray-400 focus:ring-1 focus:ring-primary focus:border-primary sm:text-sm transition-shadow w-full md:w-64"
              />
            </div>
            <button
              onClick={() => setIsCreateModalOpen(true)}
              className="inline-flex items-center justify-center px-4 py-2 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-primary hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary"
            >
              <Plus className="h-4 w-4 mr-2" />
              Create Team
            </button>
          </div>
        </div>

        {loading ? (
          <div className="flex justify-center items-center h-64">
            <svg className="animate-spin h-8 w-8 text-primary" fill="none" viewBox="0 0 24 24">
              <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
              <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
            </svg>
          </div>
        ) : (
          <LeaderboardTable
            teams={filteredTeams}
            onSaveScore={handleSaveScore}
            onDelete={(id) => setConfirmDeleteState({ isOpen: true, teamId: id })}
          />
        )}
      </main>

      <CreateTeamModal
        isOpen={isCreateModalOpen}
        onClose={() => setIsCreateModalOpen(false)}
        onSubmit={handleCreateTeam}
        isLoading={isCreating}
      />

      <ConfirmDialog
        isOpen={confirmDeleteState.isOpen}
        message="Are you sure you want to delete this team? This action cannot be undone."
        onConfirm={handleDeleteConfirm}
        onCancel={() => setConfirmDeleteState({ isOpen: false, teamId: null })}
        isLoading={isDeleting}
      />
    </div>
  );
};

export default DashboardPage;
