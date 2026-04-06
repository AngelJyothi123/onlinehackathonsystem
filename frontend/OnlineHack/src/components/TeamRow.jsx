import { Trash2 } from 'lucide-react';
import ScoreInput from './ScoreInput';

const TeamRow = ({ team, onSaveScore, onDelete }) => {
  // Members mapping - fallback to empty array
  const members = team.members || [];
  
  // Scores now correctly provided from backend
  const sprint0 = team.sprint0 != null ? team.sprint0 : 0;
  const sprint1 = team.sprint1 != null ? team.sprint1 : 0;
  const sprint2 = team.sprint2 != null ? team.sprint2 : 0;

  const totalMarks = sprint0 + sprint1 + sprint2;

  let statusText = 'Pending';
  let badgeColor = 'bg-warning text-yellow-800';
  
  if (totalMarks >= 60) {
    statusText = 'Qualified';
    badgeColor = 'bg-success text-white';
  } else if (totalMarks > 0 && totalMarks < 60) {
    statusText = 'Rejected';
    badgeColor = 'bg-danger text-white';
  }

  const handleScoreChange = (sprintNumber, val) => {
    // Send standard POST payload for a single score update
    onSaveScore(team.id, {
      sprintNumber: sprintNumber,
      marks: val
    });
  };

  return (
    <tr className="hover:bg-gray-50 bg-white transition-colors border-b border-gray-100 last:border-0 align-middle">
      <td className="px-6 py-4 whitespace-nowrap">
        <div className="font-semibold text-gray-900">{team.teamName}</div>
      </td>
      <td className="px-6 py-4">
        <div className="text-sm text-gray-500 whitespace-normal break-words max-w-xs">
          {members.map(m => typeof m === 'object' ? (m.memberName || m.name) : m).join(', ')}
        </div>
      </td>
      <td className="px-6 py-4 whitespace-nowrap">
        <ScoreInput
          initialValue={sprint0}
          onSave={(v) => handleScoreChange(0, v)}
        />
      </td>
      <td className="px-6 py-4 whitespace-nowrap">
        <ScoreInput
          initialValue={sprint1}
          onSave={(v) => handleScoreChange(1, v)}
        />
      </td>
      <td className="px-6 py-4 whitespace-nowrap">
        <ScoreInput
          initialValue={sprint2}
          onSave={(v) => handleScoreChange(2, v)}
        />
      </td>
      <td className="px-6 py-4 whitespace-nowrap font-medium text-gray-900">
        {totalMarks}
      </td>
      <td className="px-6 py-4 whitespace-nowrap">
        <span className={`px-2.5 py-1 inline-flex text-xs leading-5 font-semibold rounded-full ${badgeColor}`}>
          {team.status || statusText}
        </span>
      </td>
      <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
        <button
          onClick={() => onDelete(team.id)}
          className="text-gray-400 hover:text-danger focus:outline-none transition-colors"
          title="Delete Team"
        >
          <Trash2 className="h-5 w-5" />
        </button>
      </td>
    </tr>
  );
};

export default TeamRow;
