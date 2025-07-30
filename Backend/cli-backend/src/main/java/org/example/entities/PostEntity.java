package org.example.entities;

/*
CREATE TABLE post (
    post_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    account_id UUID REFERENCES account(account_id) ON DELETE SET NULL,
	subreddit_id UUID REFERENCES subreddit(subreddit_id) ON DELETE CASCADE,
    title TEXT NOT NULL,
    description TEXT,
    photo_path TEXT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);
 */

public class PostEntity {

}
