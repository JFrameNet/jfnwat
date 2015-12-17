SET DATABASE SQL SYNTAX MYS TRUE;

INSERT INTO Frame (ID, Name, Definition, SymbolicRep, Image, CreatedBy, CreatedDate, ModifiedDate) VALUES (1,'TestUp', 'The words in this frame take direct objects that denote entities in the world, and indicate awareness of those entities, without necessarily giving any information about the conten', NULL, NULL, 'test user', '2003-01-06 17:30:50', '2003-01-06 17:30:50' );
INSERT INTO Frame (Name, Definition, CreatedBy) VALUES ('Causation', 'The words in this frame take direct objects that denote entities in the world, and indicate awareness of those entities, without necessarily giving any information about the conten', 'alice');
INSERT INTO Frame (Name, Definition, CreatedBy) VALUES  ('Communication', 'The words in this frame take direct objects that denote entities in the world, and indicate awareness of those entities, without necessarily giving any information about the conten', 'alice');
INSERT INTO Frame (Name, Definition, CreatedBy) VALUES  ('Motion', 'The words in this frame take direct objects that denote entities in the world, and indicate awareness of those entities, without necessarily giving any information about the conten', 'alice');
INSERT INTO Frame (Name, Definition, CreatedBy) VALUES  ('Perception', 'The words in this frame take direct objects that denote entities in the world, and indicate awareness of those entities, without necessarily giving any information about the conten', 'alice');
INSERT INTO Frame (Name, Definition, CreatedBy) VALUES  ('Reciprocality', 'The words in this frame take direct objects that denote entities in the world, and indicate awareness of those entities, without necessarily giving any information about the conten', 'alice');
INSERT INTO Frame (Name, Definition, CreatedBy) VALUES ('Certainty', 'test definition', 'test user');
INSERT INTO Frame (Name, Definition, CreatedBy) VALUES ('Activity', 'test definition', 'test user');
INSERT INTO Frame (Name, Definition, CreatedBy) VALUES ('Activity_finish', 'test definition', 'test user');
INSERT INTO Frame (Name, Definition, CreatedBy) VALUES ('Activity_start', 'test definition', 'test user');
INSERT INTO Frame (Name, Definition, CreatedBy) VALUES ('Activity_stop', 'test definition', 'test user');
INSERT INTO Frame (Name, Definition, CreatedBy) VALUES ('Activity_resume', 'test definition', 'test user');
INSERT INTO Frame (Name, Definition, CreatedBy) VALUES ('Activity_pause', 'test definition', 'test user');
INSERT INTO Frame (Name, Definition, CreatedBy) VALUES ('Activity_prepare', 'test definition', 'test user');
INSERT INTO Frame (Name, Definition, CreatedBy) VALUES ('Reporting', 'test definition', 'test user');
INSERT INTO Frame (Name, Definition, CreatedBy) VALUES ('Statement', 'test definition', 'test user');
INSERT INTO Frame (Name, Definition, CreatedBy) VALUES ('Evidence', 'test definition', 'test user');
INSERT INTO Frame (Name, Definition, CreatedBy) VALUES ('Judgement', 'test definition', 'test user');
INSERT INTO Frame (Name, Definition, CreatedBy) VALUES ('Telling', 'test definition', 'test user');

INSERT INTO PartOfSpch (ID, Name, Definition) VALUES (84, 'A', 'Adjective');

INSERT INTO Lemma (ID, CreatedBy, CreatedDate, ModifiedDate, PartOfSpch_Ref) VALUES (1, 'test user', '2003-01-06 17:30:50', '2003-01-06 17:30:50', 84);

INSERT INTO LexUnit (ID, CreatedBy, Name, ImportNum, Frame_Ref, Lemma_Ref) VALUES (1,'test user', 'testLex',0, 1, 1);
INSERT INTO LexUnit (ID, CreatedBy, Name, ImportNum, Frame_Ref) VALUES (2,'shizuka', '盗む.v',0, 2);
INSERT INTO LexUnit (ID, CreatedBy, Name, ImportNum, Frame_Ref) VALUES (3,'shizuka', '回る.v',0, 2);
INSERT INTO LexUnit (ID, CreatedBy, Name, ImportNum, Frame_Ref) VALUES (4,'shizuka', 'たどりつく.v',0, 2);
INSERT INTO LexUnit (ID, CreatedBy, Name, ImportNum, Frame_Ref) VALUES (5,'shizuka', 'いい.a',0, 2);
INSERT INTO LexUnit (ID, CreatedBy, Name, ImportNum, Frame_Ref) VALUES (6,'shizuka', 'いい.a',0, 3);
INSERT INTO LexUnit (ID, CreatedBy, Name, ImportNum, Frame_Ref) VALUES (7,'shizuka', 'いい.a',0, 4);
INSERT INTO LexUnit (ID, CreatedBy, Name, ImportNum, Frame_Ref) VALUES (8,'test user', 'test.v',0, 13);
INSERT INTO LexUnit (ID, CreatedBy, Name, ImportNum, Frame_Ref) VALUES (9,'test user', 'tell apart.v',0, 15);
INSERT INTO LexUnit (ID, CreatedBy, Name, ImportNum, Frame_Ref) VALUES (10,'test user', 'tell.v',0, 16);
INSERT INTO LexUnit (ID, CreatedBy, Name, ImportNum, Frame_Ref) VALUES (11,'test user', 'tell.v',0, 17);
INSERT INTO LexUnit (ID, CreatedBy, Name, ImportNum, Frame_Ref) VALUES (12,'test user', 'telling off.n',0, 18);


INSERT INTO StatusType (ID,Name, Description, Rank) VALUES (1, 'New', 'LU defined and sentences ready for annotation', 20);
INSERT INTO StatusType (ID,Name, Description, Rank) VALUES (2, 'Created', 'LU defined in frame, but sentences not yet extracted from corpus', 20);
INSERT INTO StatusType (ID,Name, Description, Rank) VALUES (3, 'Finished_Checked', 'Annotation and final checking completed during FrameNet II', 20);

INSERT INTO Status (ID, Text, CreatedBy, ModifiedDate, LexUnit_Ref, StatusType_Ref) VALUES (10021, NULL, 'shizuka', '2005-05-11 18:56:21', 1, 1);
INSERT INTO Status (ID, Text, CreatedBy, ModifiedDate, LexUnit_Ref, StatusType_Ref) VALUES (10022, NULL, 'shizuka', '2005-05-11 18:56:21', 2, 2);
INSERT INTO Status (ID, Text, CreatedBy, ModifiedDate, LexUnit_Ref, StatusType_Ref) VALUES (10023, NULL, 'shizuka', '2005-05-11 18:56:21', 3, 2);
INSERT INTO Status (ID, Text, CreatedBy, ModifiedDate, LexUnit_Ref, StatusType_Ref) VALUES (10024, NULL, 'shizuka', '2005-05-11 18:56:21', 4, 2);
INSERT INTO Status (ID, Text, CreatedBy, ModifiedDate, LexUnit_Ref, StatusType_Ref) VALUES (10028, NULL, 'shizuka', '2005-05-11 18:56:21', 4, 3);
INSERT INTO Status (ID, Text, CreatedBy, ModifiedDate, LexUnit_Ref, StatusType_Ref) VALUES (10025, NULL, 'shizuka', '2005-05-11 18:56:21', 5, 3);
INSERT INTO Status (ID, Text, CreatedBy, ModifiedDate, LexUnit_Ref, StatusType_Ref) VALUES (10026, NULL, 'shizuka', '2005-05-11 18:56:21', 6, 2);
INSERT INTO Status (ID, Text, CreatedBy, ModifiedDate, LexUnit_Ref, StatusType_Ref) VALUES (10027, NULL, 'shizuka', '2005-05-11 18:56:21', 7, 2);
INSERT INTO Status (ID, Text, CreatedBy, ModifiedDate, LexUnit_Ref, StatusType_Ref) VALUES (10033, NULL, 'shizuka', '2005-05-11 18:56:21', 8, 2);
INSERT INTO Status (ID, Text, CreatedBy, ModifiedDate, LexUnit_Ref, StatusType_Ref) VALUES (10029, NULL, 'shizuka', '2005-05-11 18:56:21', 9, 1);
INSERT INTO Status (ID, Text, CreatedBy, ModifiedDate, LexUnit_Ref, StatusType_Ref) VALUES (10030, NULL, 'shizuka', '2005-05-11 18:56:21', 10, 1);
INSERT INTO Status (ID, Text, CreatedBy, ModifiedDate, LexUnit_Ref, StatusType_Ref) VALUES (10031, NULL, 'shizuka', '2005-05-11 18:56:21', 11, 2);
INSERT INTO Status (ID, Text, CreatedBy, ModifiedDate, LexUnit_Ref, StatusType_Ref) VALUES (10032, NULL, 'shizuka', '2005-05-11 18:56:21', 12, 3);
INSERT INTO Status (ID, Text, CreatedBy, ModifiedDate, LexUnit_Ref, StatusType_Ref) VALUES (10034, NULL, 'shizuka', '2005-05-11 18:56:21', 12, 2);

# INSERT INTO Document (ID, Author, Name, Description, CreatedDate, CreatedBy, ModifiedDate) VALUES (10061, NULL, 'sb100042.KNP', 'sb100042.KNP', '2005-11-03 03:21:56', 'shizuka', '2015-07-16 08:18:52');

INSERT INTO Corpus (ID, Name, Description) VALUES (1, 'BCCWJ_CORE_OC_YAHOO_知恵袋_v1_1', 'BCCWJ v1.1 CORE Data Yahoo!知恵袋 Q&A | NumTrans 非適用 | Disc3_CORE_OT_core_M-XML')
INSERT INTO Corpus (ID, Name, Description) VALUES (2, 'BCCWJ_CORE_OW_白書_v1_1', 'BCCWJ v1.1 CORE Data Yahoo!知恵袋 Q&A | NumTrans 非適用 | Disc3_CORE_OT_core_M-XML')
INSERT INTO Corpus (ID, Name, Description) VALUES (3, 'BCCWJ_CORE_PB_書籍_v1_1', 'BCCWJ v1.1 CORE Data Yahoo!知恵袋 Q&A | NumTrans 非適用 | Disc3_CORE_OT_core_M-XML')