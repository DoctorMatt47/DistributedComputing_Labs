INSERT INTO Producer (id, name)
VALUES
    (100, 'Jetbrains'),
    (101, 'Microsoft'),
    (103, 'Netflix Inc.'),
    (104, 'Apple');

INSERT INTO Product (id, producerId, name, price, type)
VALUES
    (100, 100, 'Rider', 150, 'DESKTOP'),
    (101, 100, 'Idea', 150, 'DESKTOP'),
    (102, 100, 'Pycharm', 120, 'DESKTOP'),
    (200, 101, 'Office', 20, 'DESKTOP'),
    (201, 101, 'VisualStudio', 0, 'DESKTOP'),
    (300, 103, 'Netflix', 5, 'WEB'),
    (400, 104, 'ITunes', 0, 'MOBILE'),
    (401, 104, 'AppStore', 0, 'MOBILE')