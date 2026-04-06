import { useState } from 'react';
import { X, Plus, Minus } from 'lucide-react';

const CreateTeamModal = ({ isOpen, onClose, onSubmit, isLoading }) => {
  const [name, setName] = useState('');
  const [members, setMembers] = useState(['']); // Start with 1 member
  const [error, setError] = useState(null);

  if (!isOpen) return null;

  const handleAddMember = () => {
    if (members.length < 4) {
      setMembers([...members, '']);
    }
  };

  const handleRemoveMember = (index) => {
    const newMembers = [...members];
    newMembers.splice(index, 1);
    setMembers(newMembers);
  };

  const handleMemberChange = (index, value) => {
    const newMembers = [...members];
    newMembers[index] = value;
    setMembers(newMembers);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setError(null);
    
    if (!name.trim()) {
      setError('Team name is required.');
      return;
    }

    const filteredMembers = members.filter(m => m.trim() !== '');
    if (filteredMembers.length === 0) {
      setError('At least one member is required.');
      return;
    }

    // Backend typically expects list of objects or strings. Let's send objects assuming { name: string } or just strings.
    // I'll send strings assuming backend is flexible, if it needs objects, map accordingly.
    // Given the TeamRow maps over m.name if object or just m if string. We will send an array of objects since that's a common JPA structure.
    const mappedMembers = filteredMembers.map(m => ({ name: m.trim() }));

    onSubmit({ name: name.trim(), members: mappedMembers }, () => {
      // onSuccess callback
      setName('');
      setMembers(['']);
      onClose();
    });
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-gray-900/50 backdrop-blur-sm">
      <div className="bg-white rounded-xl shadow-2xl w-full max-w-md overflow-hidden flex flex-col max-h-[90vh]">
        <div className="flex justify-between items-center p-5 border-b border-gray-100">
          <h2 className="text-xl font-semibold text-gray-900">Create New Team</h2>
          <button onClick={onClose} className="text-gray-400 hover:text-gray-600 focus:outline-none">
            <X className="h-5 w-5" />
          </button>
        </div>
        
        <form onSubmit={handleSubmit} className="flex flex-col overflow-y-auto">
          <div className="p-5 flex-1 space-y-4">
            {error && (
              <div className="p-3 text-sm text-danger bg-red-50 rounded-md border border-red-100">
                {error}
              </div>
            )}
            
            <div>
              <label htmlFor="teamName" className="block text-sm font-medium text-gray-700 mb-1">
                Team Name
              </label>
              <input
                id="teamName"
                type="text"
                value={name}
                onChange={(e) => setName(e.target.value)}
                className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
                placeholder="Enter team name"
              />
            </div>

            <div>
              <div className="flex justify-between items-center mb-1">
                <label className="block text-sm font-medium text-gray-700">
                  Members (Max 4)
                </label>
              </div>
              
              <div className="space-y-2">
                {members.map((member, index) => (
                  <div key={index} className="flex gap-2">
                    <input
                      type="text"
                      value={member}
                      onChange={(e) => handleMemberChange(index, e.target.value)}
                      className="flex-1 px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
                      placeholder={`Member ${index + 1} Name`}
                    />
                    {members.length > 1 && (
                      <button
                        type="button"
                        onClick={() => handleRemoveMember(index)}
                        className="p-2 text-gray-400 hover:text-danger focus:outline-none border border-gray-300 rounded-md hover:bg-gray-50"
                      >
                        <Minus className="h-5 w-5" />
                      </button>
                    )}
                  </div>
                ))}
              </div>
              
              {members.length < 4 && (
                <button
                  type="button"
                  onClick={handleAddMember}
                  className="mt-2 text-sm font-medium text-primary hover:text-blue-700 flex items-center focus:outline-none"
                >
                  <Plus className="h-4 w-4 mr-1" />
                  Add Another Member
                </button>
              )}
            </div>
          </div>
          
          <div className="p-5 border-t border-gray-100 bg-gray-50 flex justify-end gap-3">
            <button
              type="button"
              onClick={onClose}
              disabled={isLoading}
              className="px-4 py-2 border border-gray-300 shadow-sm text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary disabled:opacity-50"
            >
              Cancel
            </button>
            <button
              type="submit"
              disabled={isLoading}
              className="px-4 py-2 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-primary hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary disabled:opacity-50 flex items-center"
            >
              {isLoading ? (
                <svg className="animate-spin -ml-1 mr-2 h-4 w-4 text-white" fill="none" viewBox="0 0 24 24">
                  <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                  <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                </svg>
              ) : null}
              Create Team
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default CreateTeamModal;
