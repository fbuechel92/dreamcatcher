
-- Insert into user table
INSERT INTO dreamdb.user (email, password, name, gender, birthdate, country, occupation)
VALUES
    ('alice@example.com', 'password123', 'Alice Smith', 'Female', '1990-05-15', 'USA', 'Engineer'),
    ('bob@example.com', 'securePass', 'Bob Johnson', 'Male', '1985-03-22', 'Canada', 'Designer');

-- Insert into theory table
INSERT INTO dreamdb.theory (theory_name, theory_description)
VALUES
    ('Lucid Dreaming', 'The ability to be aware of and control dreams.'),
    ('Dream Interpretation', 'Analyzing dreams to find hidden meanings.');

-- Insert into sleep table
INSERT INTO dreamdb.sleep (sleep_quality, sleep_length)
VALUES
    (8, 7),
    (6, 5);

-- Insert into dream_analysis table
INSERT INTO dreamdb.dream_analysis (user_id, dream_title, dream_theme, interpretation, implications)
VALUES
    (1, 'Flying High', 'Freedom', 'Represents a desire for freedom and escape.', 'Consider exploring new opportunities.'),
    (2, 'Lost in the Woods', 'Confusion', 'Indicates feeling lost or uncertain.', 'Focus on clarifying your goals.');

-- Insert into dream_trend_analysis table
INSERT INTO dreamdb.dream_trend_analysis (user_id, trend, interpretation, implications)
VALUES
    (1, 'Recurring Dreams', 'Recurring dreams suggest unresolved issues.', 'Address ongoing problems.'),
    (2, 'Nightmares', 'Nightmares may indicate stress or anxiety.', 'Consider stress management techniques.');

-- Insert into dream table
INSERT INTO dreamdb.dream (user_id, dream_analysis_id, sleep_id, theory_id, visitor, plot, location, mood, additional_info)
VALUES
    (1, 1, 1, 1, 'A mysterious figure', 'Flying over mountains', 'Mountain range', 'Excited', 'First lucid dream experience.'),
    (2, 2, 2, 2, 'An old friend', 'Lost in a forest', 'Dense woods', 'Anxious', 'Woke up feeling confused.');
