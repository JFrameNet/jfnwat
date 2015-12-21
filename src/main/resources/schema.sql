SET DATABASE SQL SYNTAX MYS TRUE;

DROP TABLE Frame IF EXISTS;
CREATE TABLE Frame (
  ID int GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
  Name varchar(80) NOT NULL UNIQUE,
  Definition varchar(255) DEFAULT NULL,
  SymbolicRep varchar(80) DEFAULT NULL,
  Image mediumblob,
  CreatedBy varchar(40) NOT NULL,
  CreatedDate timestamp DEFAULT NULL,
  ModifiedDate timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL
);

DROP TABLE IF EXISTS PartOfSpch;
CREATE TABLE PartOfSpch (
  ID int GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
  Name varchar(80) DEFAULT NULL,
  Definition varchar(255) DEFAULT NULL
);

DROP TABLE IF EXISTS Lemma;
CREATE TABLE Lemma (
  ID int GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
  CreatedBy varchar(40) NOT NULL,
  CreatedDate timestamp DEFAULT NULL,
  ModifiedDate timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
  PartOfSpch_Ref int DEFAULT NULL
);

DROP TABLE LexUnit IF EXISTS;
CREATE TABLE LexUnit (
  ID int GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
  Name varchar(80) NOT NULL,
  SenseDesc varchar(255) DEFAULT NULL,
  Frame_Ref int NOT NULL,
  Lemma_Ref int NOT NULL,
  CreatedBy varchar(40) NOT NULL,
  CreatedDate timestamp DEFAULT NULL,
  ModifiedDate timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
  IncorporatedFE int DEFAULT 0 ,
  ImportNum int DEFAULT 0, 
);

DROP TABLE IF EXISTS StatusType;
CREATE TABLE StatusType (
  ID int GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
  Name varchar(40) DEFAULT NULL,
  Description varchar(255) DEFAULT NULL,
  Icon blob,
  Rank int DEFAULT NULL
);

DROP TABLE IF EXISTS Status;
CREATE TABLE Status (
  ID int GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
  Text varchar(255) DEFAULT NULL,
  CreatedDate timestamp DEFAULT NULL,
  CreatedBy varchar(40) NOT NULL,
  ModifiedDate timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
  StatusType_Ref int DEFAULT NULL,
  LexUnitRef int DEFAULT NULL
);

DROP TABLE IF EXISTS Document;
CREATE TABLE Document (
  ID int GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
  Author varchar(80) DEFAULT NULL,
  Name varchar(80) DEFAULT NULL,
  Description varchar(255) DEFAULT NULL,
  Corpus_Ref int DEFAULT 0,
  CreatedDate timestamp DEFAULT NULL,
  CreatedBy varchar(40) NOT NULL,
  ModifiedDate timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL
);

DROP TABLE IF EXISTS Corpus;
CREATE TABLE Corpus (
  ID int GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
  Name varchar(80) NOT NULL,
  Description varchar(255) DEFAULT NULL,
  CreatedDate timestamp DEFAULT NULL,
  CreatedBy varchar(40) NOT NULL,
  ModifiedDate timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL

);

DROP TABLE IF EXISTS FrameElement;
CREATE TABLE FrameElement (
  ID int GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
  Name varchar(80) NOT NULL,
  Abbrev varchar(40) NOT NULL,
  Definition varchar(255) DEFAULT NULL,
  Frame_Ref int NOT NULL,
  SemRoleRank int DEFAULT 0,
  Type VARCHAR(40) DEFAULT NULL,
  Core CHAR DEFAULT 'N',
  CreatedDate timestamp DEFAULT NULL,
  CreatedBy varchar(40) NOT NULL,
  ModifiedDate timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL
);

DROP TABLE IF EXISTS RelationType;
CREATE TABLE RelationType (
  ID int GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
  Name varchar(80) DEFAULT NULL,
  Description varchar(255) DEFAULT NULL,
  SuperFrameName varchar(80) DEFAULT NULL,
  SubFrameName varchar(80) DEFAULT NULL,
  Profiles int DEFAULT 0,
  Complete int DEFAULT 0
);

DROP TABLE IF EXISTS FERelationType;
CREATE TABLE FERelationType (
  ID int GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
  Name varchar(80) DEFAULT NULL,
  Description varchar(255) DEFAULT NULL,
  RelationType_Ref int DEFAULT NULL
);

DROP TABLE IF EXISTS FrameRelation;
CREATE TABLE FrameRelation (
  ID int GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
  Profiled int DEFAULT 0,
  SuperFrame_Ref int DEFAULT NULL,
  SubFrame_Ref int DEFAULT NULL,
  RelationType_Ref int DEFAULT NULL,
  Name varchar(80) DEFAULT NULL,
  CreatedDate timestamp DEFAULT NULL,
  CreatedBy varchar(40) NOT NULL,
  ModifiedDate timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL
);

DROP TABLE IF EXISTS FERelation;
CREATE TABLE FERelation (
  ID                    INT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
  SuperFrameElement_Ref INT       DEFAULT NULL,
  SubFrameElement_Ref   INT       DEFAULT NULL,
  FrameRelation_Ref     INT       DEFAULT NULL,
  CreatedDate           TIMESTAMP DEFAULT NULL,
  FERelationType_Ref    INT       DEFAULT NULL,
  CreatedBy             VARCHAR(40)                         NOT NULL,
  ModifiedDate          TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);