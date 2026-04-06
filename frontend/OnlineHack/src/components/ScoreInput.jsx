import { useState, useEffect } from 'react';

const ScoreInput = ({ initialValue, onSave, isLoading }) => {
  const [value, setValue] = useState(initialValue !== null ? initialValue : '');

  useEffect(() => {
    setValue(initialValue !== null ? initialValue : '');
  }, [initialValue]);

  const handleBlur = () => {
    const val = value !== '' ? Number(value) : null;
    if (val !== initialValue) {
      onSave(val);
    }
  };

  return (
    <input
      type="number"
      value={value}
      onChange={(e) => setValue(e.target.value)}
      onBlur={handleBlur}
      disabled={isLoading}
      className="w-16 px-2 py-1 text-sm border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent disabled:bg-gray-100 disabled:cursor-not-allowed"
      min="0"
    />
  );
};

export default ScoreInput;
