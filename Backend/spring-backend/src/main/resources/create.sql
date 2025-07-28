CREATE TYPE votable_type AS ENUM ('post', 'comment');

CREATE TYPE parent_type AS ENUM ('post', 'comment');

CREATE TYPE vote_type_enum AS ENUM ('up', 'down');


CREATE TABLE account (
    account_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username TEXT UNIQUE NOT NULL,
    email TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    photo_path TEXT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE subreddit (
    subreddit_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    account_id UUID REFERENCES account(account_id) ON DELETE SET NULL,
    name TEXT UNIQUE NOT NULL,
    description TEXT,
	member_count INTEGER DEFAULT 0,
	post_count INTEGER DEFAULT 0,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE post (
    post_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    account_id UUID REFERENCES account(account_id) ON DELETE SET NULL,
	subreddit_id UUID REFERENCES subreddit(subreddit_id) ON DELETE CASCADE,
    title TEXT NOT NULL,
    content TEXT,
    photo_path TEXT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    upvotes INTEGER,
    downvotes INTEGER,
    score INTEGER,
    comment_count INTEGER
);

CREATE TABLE comment (
    comment_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    account_id UUID REFERENCES account(account_id) ON DELETE SET NULL,
    post_id UUID REFERENCES post(post_id) ON DELETE CASCADE,
    parent_comment_id UUID REFERENCES comment(comment_id) ON DELETE CASCADE,
    text TEXT NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    upvotes INTEGER,
    downvotes INTEGER,
    score INTEGER
);

CREATE TABLE vote (
    vote_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    votable_id UUID NOT NULL, -- post_id sau comment_id
    account_id UUID NOT NULL REFERENCES account(account_id) ON DELETE CASCADE,
    votable_type votable_type NOT NULL, -- 'post' sau 'comment'
    vote_type vote_type_enum NOT NULL,  -- 'up' sau 'down'
    UNIQUE(account_id, votable_id)
);