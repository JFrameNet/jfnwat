SET DATABASE SQL SYNTAX MYS TRUE;

INSERT INTO Frame (ID, Name, Definition, SymbolicRep, Image, CreatedBy, CreatedDate, ModifiedDate) VALUES (1,'Test', 'The words in this frame take direct objects that denote entities in the world, and indicate awareness of those entities, without necessarily giving any information about the conten', NULL, NULL, 'test user', '2003-01-06 17:30:50', '2003-01-06 17:30:50' );
INSERT INTO Frame (Name, Definition, CreatedBy) VALUES ('Causation', 'The words in this frame take direct objects that denote entities in the world, and indicate awareness of those entities, without necessarily giving any information about the conten', 'alice');
INSERT INTO Frame (Name, Definition, CreatedBy) VALUES  ('Communication', 'The words in this frame take direct objects that denote entities in the world, and indicate awareness of those entities, without necessarily giving any information about the conten', 'alice');
INSERT INTO Frame (Name, Definition, CreatedBy) VALUES  ('Motion', 'The words in this frame take direct objects that denote entities in the world, and indicate awareness of those entities, without necessarily giving any information about the conten', 'alice');
INSERT INTO Frame (Name, Definition, CreatedBy) VALUES  ('Perception', 'The words in this frame take direct objects that denote entities in the world, and indicate awareness of those entities, without necessarily giving any information about the conten', 'alice');
INSERT INTO Frame (Name, Definition, CreatedBy) VALUES  ('Reciprocality', 'The words in this frame take direct objects that denote entities in the world, and indicate awareness of those entities, without necessarily giving any information about the conten', 'alice');

INSERT INTO PartOfSpch (ID, Name, Definition) VALUES (84, 'A', 'Adjective');

INSERT INTO Lemma (ID, CreatedBy, CreatedDate, ModifiedDate, PartOfSpch_Ref) VALUES (1, 'test user', '2003-01-06 17:30:50', '2003-01-06 17:30:50', 84);

INSERT INTO LexUnit (ID, CreatedBy, Name, ImportNum, Frame_Ref, Lemma_Ref) VALUES (1,'bob', 'testLex',0, 1, 1);
INSERT INTO LexUnit (ID, CreatedBy, Name, ImportNum, Frame_Ref) VALUES (2,'shizuka', '盗む.v',0, 2);
INSERT INTO LexUnit (ID, CreatedBy, Name, ImportNum, Frame_Ref) VALUES (3,'shizuka', '回る.v',0, 2);
INSERT INTO LexUnit (ID, CreatedBy, Name, ImportNum, Frame_Ref) VALUES (4,'shizuka', 'たどりつく.v',0, 2);
INSERT INTO LexUnit (ID, CreatedBy, Name, ImportNum, Frame_Ref) VALUES (5,'shizuka', 'いい.a',0, 2);
INSERT INTO LexUnit (ID, CreatedBy, Name, ImportNum, Frame_Ref) VALUES (6,'shizuka', 'いい.a',0, 3);
INSERT INTO LexUnit (ID, CreatedBy, Name, ImportNum, Frame_Ref) VALUES (7,'shizuka', 'いい.a',0, 4);

INSERT INTO StatusType (ID,Name, Description, Rank) VALUES (1, 'New', 'Annotation and final checking completed during FrameNet II', 20);
INSERT INTO StatusType (ID,Name, Description, Rank) VALUES (2, 'Created', 'Annotation and final checking completed during FrameNet II', 20);
INSERT INTO StatusType (ID,Name, Description, Rank) VALUES (3, 'Finished_Checked', 'Annotation and final checking completed during FrameNet II', 20);

INSERT INTO Status (ID, Text, CreatedBy, ModifiedDate, LexUnit_Ref, StatusType_Ref) VALUES (10021, NULL, 'shizuka', '2005-05-11 18:56:21', 1, 1);
INSERT INTO Status (ID, Text, CreatedBy, ModifiedDate, LexUnit_Ref, StatusType_Ref) VALUES (10022, NULL, 'shizuka', '2005-05-11 18:56:21', 2, 2);
INSERT INTO Status (ID, Text, CreatedBy, ModifiedDate, LexUnit_Ref, StatusType_Ref) VALUES (10023, NULL, 'shizuka', '2005-05-11 18:56:21', 3, 2);
INSERT INTO Status (ID, Text, CreatedBy, ModifiedDate, LexUnit_Ref, StatusType_Ref) VALUES (10024, NULL, 'shizuka', '2005-05-11 18:56:21', 4, 2);
INSERT INTO Status (ID, Text, CreatedBy, ModifiedDate, LexUnit_Ref, StatusType_Ref) VALUES (10028, NULL, 'shizuka', '2005-05-11 18:56:21', 4, 3);
INSERT INTO Status (ID, Text, CreatedBy, ModifiedDate, LexUnit_Ref, StatusType_Ref) VALUES (10025, NULL, 'shizuka', '2005-05-11 18:56:21', 5, 3);
INSERT INTO Status (ID, Text, CreatedBy, ModifiedDate, LexUnit_Ref, StatusType_Ref) VALUES (10026, NULL, 'shizuka', '2005-05-11 18:56:21', 6, 2);
INSERT INTO Status (ID, Text, CreatedBy, ModifiedDate, LexUnit_Ref, StatusType_Ref) VALUES (10027, NULL, 'shizuka', '2005-05-11 18:56:21', 7, 2);

INSERT INTO Document (ID, Author, Name, Description, CreatedDate, CreatedBy, ModifiedDate) VALUES (10061, NULL, 'sb100042.KNP', 'sb100042.KNP', '2005-11-03 03:21:56', 'shizuka', '2015-07-16 08:18:52');
