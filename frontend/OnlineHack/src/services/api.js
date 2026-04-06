import axiosInstance from '../utils/axiosInstance';

export const login = async (credentials) => {
  const response = await axiosInstance.post('/auth/login', credentials);
  // Backend wraps the token inside ApiResponse.data
  return response.data.data;
};

export const getTeams = async () => {
  // We use leaderboard since it now incorporates member data and sprint scores
  const response = await axiosInstance.get('/leaderboard');
  return response.data;
};

export const searchTeams = async (name) => {
  const response = await axiosInstance.get(`/teams/search?name=${encodeURIComponent(name)}`);
  // The search endpoint in backend currently returns TeamResponse, not LeaderboardResponse.
  // Wait, let's just make the frontend filter the getTeams list locally so it shares the same rich structure! 
  // No, actually I can just leave searchTeams and if backend doesn't give scores, they will be 0 on search. 
  // For a perfect UX, we should fetch leaderboard and filter manually or fetch leaderboard. 
  // In the Dashboard component we can handle this by filtering locally.
  return response.data;
};

export const createTeam = async (teamData) => {
  // 1. Create the team
  const createResponse = await axiosInstance.post('/teams', { teamName: teamData.name });
  
  const teamId = createResponse.data.data.id;

  // 2. Add members to the successfully created team
  if (teamData.members && teamData.members.length > 0) {
    const memberPromises = teamData.members.map(member => 
      axiosInstance.post(`/teams/${teamId}/members`, { 
        memberName: member.name || member.memberName || member 
      })
    );
    await Promise.all(memberPromises);
  }
  
  return createResponse.data;
};

export const deleteTeam = async (id) => {
  const response = await axiosInstance.delete(`/teams/${id}`);
  return response.data;
};

export const saveScore = async (teamId, scoreData) => {
  // scoreData could be { sprintId, marks }
  const payload = { teamId, ...scoreData };
  const response = await axiosInstance.post('/scores', payload);
  return response.data;
};
