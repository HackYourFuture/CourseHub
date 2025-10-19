-- Add API key column to user_account table
ALTER TABLE user_account ADD COLUMN api_key VARCHAR(100) NULL;
CREATE UNIQUE INDEX idx_api_key_unique ON user_account (api_key) WHERE api_key IS NOT NULL;

