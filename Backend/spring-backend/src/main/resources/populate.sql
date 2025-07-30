-- Populare tabela account
INSERT INTO account (account_id, username, email, password) VALUES
                                                                (gen_random_uuid(), 'user1', 'user1@email.com', 'pass1'),
                                                                (gen_random_uuid(), 'user2', 'user2@email.com', 'pass2');

-- Populare tabela subreddit
INSERT INTO subreddit (subreddit_id, account_id, name, description) VALUES
                                                                        (gen_random_uuid(), (SELECT account_id FROM account LIMIT 1), 'java', 'Totul despre Java'),
                                                                        (gen_random_uuid(), (SELECT account_id FROM account OFFSET 1 LIMIT 1), 'spring', 'Spring Framework');

-- Populare tabela post
INSERT INTO post (post_id, account_id, subreddit_id, title, content) VALUES
                                                                         (gen_random_uuid(), (SELECT account_id FROM account LIMIT 1), (SELECT subreddit_id FROM subreddit LIMIT 1), 'Salutare!', 'Primul post pe subreddit-ul java'),
                                                                         (gen_random_uuid(), (SELECT account_id FROM account OFFSET 1 LIMIT 1), (SELECT subreddit_id FROM subreddit OFFSET 1 LIMIT 1), 'Spring e tare', 'Hai să discutăm despre Spring!');

-- Populare tabela comment
INSERT INTO comment (comment_id, account_id, post_id, text) VALUES
                                                                (gen_random_uuid(), (SELECT account_id FROM account LIMIT 1), (SELECT post_id FROM post LIMIT 1), 'Super postare!'),
                                                                (gen_random_uuid(), (SELECT account_id FROM account OFFSET 1 LIMIT 1), (SELECT post_id FROM post LIMIT 1), 'Mulțumesc!');

-- Populare tabela vote
INSERT INTO vote (vote_id, votable_id, account_id, votable_type, vote_type) VALUES
                                                                                (gen_random_uuid(), (SELECT post_id FROM post LIMIT 1), (SELECT account_id FROM account LIMIT 1), 'post', 'up'),
                                                                                (gen_random_uuid(), (SELECT comment_id FROM comment LIMIT 1), (SELECT account_id FROM account OFFSET 1 LIMIT 1), 'comment', 'up');