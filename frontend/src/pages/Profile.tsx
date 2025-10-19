import React, {useState} from 'react';
import {User} from '../types/User';
import {Navigate} from 'react-router';
import {useConfig} from '../ConfigContext';

function Profile({ user }: { user: User | null }) {
  const [apiKey, setApiKey] = useState<string | null>(null);
  const [isGenerating, setIsGenerating] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const {backendUrl} = useConfig();

  if (!user) {
    return <Navigate to="/login" />;
  }

  const generateApiKey = async () => {
    setIsGenerating(true);
    setError(null);

    try {
      const response = await fetch(`${backendUrl}/generate-api-key`, {
        method: 'POST',
        credentials: 'include',
      });

      if (!response.ok) {
        throw new Error('Failed to generate API key');
      }

      const data = await response.json();
      setApiKey(data.apiKey);
    } catch (err) {
      setError('Failed to generate API key. Please try again later.');
    } finally {
      setIsGenerating(false);
    }
  };

  return (
    <div className="max-w-5xl container mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-6">User Profile</h1>

      <div className="bg-white shadow-md rounded-lg p-6 mb-6">
        <h2 className="text-xl font-semibold mb-4">Personal Information</h2>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <p className="text-sm text-gray-500">First Name</p>
            <p className="font-medium">{user.firstName}</p>
          </div>
          <div>
            <p className="text-sm text-gray-500">Last Name</p>
            <p className="font-medium">{user.lastName}</p>
          </div>
          <div>
            <p className="text-sm text-gray-500">Role</p>
            <p className="font-medium">{user.role}</p>
          </div>
          <div>
            <p className="text-sm text-gray-500">Email</p>
            <p className="font-medium">{user.emailAddress}</p>
          </div>
        </div>
      </div>

      <div className="bg-white shadow-md rounded-lg p-6">
        <h2 className="text-xl font-semibold mb-4">API Access</h2>
        <p className="text-gray-600 mb-4">
          Generate an API key to access the CourseHub APIs programmatically. Keep this key secure!
        </p>

        {apiKey ? (
          <div className="mb-4">
            <p className="text-sm text-gray-500 mb-1">Your API Key</p>
            <div className="flex items-center">
              <input
                type="text"
                value={apiKey}
                readOnly
                className="flex-1 border rounded-md py-2 px-3 bg-gray-50"
              />
              <button
                className="ml-2 bg-gray-200 hover:bg-gray-300 px-4 py-2 rounded-md"
                onClick={() => {
                  navigator.clipboard.writeText(apiKey);
                }}
              >
                Copy
              </button>
            </div>
            <p className="text-sm text-red-500 mt-2">
              Make sure to save this key now! You won't be able to see it again.
            </p>
          </div>
        ) : (
          <>
            {error && <p className="text-red-500 mb-4">{error}</p>}
            <button
              onClick={generateApiKey}
              disabled={isGenerating}
              className="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 transition disabled:bg-blue-400"
            >
              {isGenerating ? 'Generating...' : 'Generate API Key'}
            </button>
          </>
        )}
      </div>
    </div>
  );
}

export default Profile;
