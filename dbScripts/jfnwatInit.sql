# ************************************************************
# Sequel Pro SQL dump
# Version 4500
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: localhost (MySQL 5.5.46)
# Database: jfnwat
# Generation Time: 2016-03-09 06:53:57 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table AnnotationSet
# ------------------------------------------------------------

DROP TABLE IF EXISTS `AnnotationSet`;

CREATE TABLE `AnnotationSet` (
  `ID` int(11) NOT NULL,
  `CurrentAnnoStatus_Ref` int(11) NOT NULL,
  `Construction_Ref` int(11) DEFAULT NULL,
  `LexUnit_Ref` int(11) DEFAULT NULL,
  `SubCorpus_Ref` int(11) DEFAULT NULL,
  `Sentence_Ref` int(11) NOT NULL,
  `CreatedBy` varchar(40) NOT NULL,
  `CreatedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `CurrentAnnoStatus_Ref` (`CurrentAnnoStatus_Ref`),
  KEY `Construction_Ref` (`Construction_Ref`),
  KEY `LexUnit_Ref` (`LexUnit_Ref`),
  KEY `SubCorpus_Ref` (`SubCorpus_Ref`),
  KEY `Sentence_Ref` (`Sentence_Ref`),
  CONSTRAINT `annotationset_ibfk_5` FOREIGN KEY (`Sentence_Ref`) REFERENCES `Sentence` (`ID`),
  CONSTRAINT `annotationset_ibfk_1` FOREIGN KEY (`CurrentAnnoStatus_Ref`) REFERENCES `AnnotationStatus` (`ID`),
  CONSTRAINT `annotationset_ibfk_2` FOREIGN KEY (`Construction_Ref`) REFERENCES `Construction` (`ID`),
  CONSTRAINT `annotationset_ibfk_3` FOREIGN KEY (`LexUnit_Ref`) REFERENCES `LexUnit` (`ID`),
  CONSTRAINT `annotationset_ibfk_4` FOREIGN KEY (`SubCorpus_Ref`) REFERENCES `SubCorpus` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table AnnotationStatus
# ------------------------------------------------------------

DROP TABLE IF EXISTS `AnnotationStatus`;

CREATE TABLE `AnnotationStatus` (
  `ID` int(11) NOT NULL,
  `Name` varchar(40) NOT NULL,
  `Description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Color
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Color`;

CREATE TABLE `Color` (
  `ID` int(11) NOT NULL,
  `Name` varchar(80) NOT NULL,
  `RGB` varchar(10) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table ConstructElement
# ------------------------------------------------------------

DROP TABLE IF EXISTS `ConstructElement`;

CREATE TABLE `ConstructElement` (
  `ID` int(11) NOT NULL,
  `Name` varchar(80) NOT NULL,
  `Abbrev` varchar(40) DEFAULT NULL,
  `Definition` varchar(255) DEFAULT NULL,
  `Construction_Ref` int(11) NOT NULL,
  `CreatedBy` varchar(40) NOT NULL,
  `CreatedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `Construction_Ref` (`Construction_Ref`),
  CONSTRAINT `constructelement_ibfk_1` FOREIGN KEY (`Construction_Ref`) REFERENCES `construction` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Construction
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Construction`;

CREATE TABLE `Construction` (
  `ID` int(11) NOT NULL,
  `Name` varchar(80) NOT NULL,
  `Definition` varchar(255) DEFAULT NULL,
  `Image` mediumblob,
  `SymbolicRep` varchar(80) DEFAULT NULL,
  `ModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `CreatedBy` varchar(40) NOT NULL DEFAULT '',
  `CreatedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Corpus
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Corpus`;

CREATE TABLE `Corpus` (
  `ID` int(11) NOT NULL,
  `Name` varchar(80) NOT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `CreatedBy` varchar(40) NOT NULL,
  `CreatedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Document
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Document`;

CREATE TABLE `Document` (
  `ID` int(11) NOT NULL,
  `Name` varchar(80) NOT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `Author` varchar(80) DEFAULT NULL,
  `Corpus_Ref` int(11) NOT NULL,
  `CreatedBy` varchar(40) NOT NULL,
  `CreatedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `Corpus_Ref` (`Corpus_Ref`),
  CONSTRAINT `document_ibfk_1` FOREIGN KEY (`Corpus_Ref`) REFERENCES `corpus` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Document_Genre
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Document_Genre`;

CREATE TABLE `Document_Genre` (
  `Genre_Ref` int(11) NOT NULL DEFAULT '0',
  `Document_Ref` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Genre_Ref`,`Document_Ref`),
  CONSTRAINT `document_genre_ibfk_1` FOREIGN KEY (`Genre_Ref`) REFERENCES `genre` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



# Dump of table FERelation
# ------------------------------------------------------------

DROP TABLE IF EXISTS `FERelation`;

CREATE TABLE `FERelation` (
  `ID` int(11) NOT NULL,
  `FrameRelation_Ref` int(11) NOT NULL,
  `SuperFrameElement_Ref` int(11) NOT NULL,
  `SubFrameElement_Ref` int(11) NOT NULL,
  `FERelationType_Ref` int(11) DEFAULT NULL,
  `CreatedBy` varchar(40) NOT NULL,
  `CreatedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `FrameRelation_Ref` (`FrameRelation_Ref`),
  KEY `SuperFrameElement_Ref` (`SuperFrameElement_Ref`),
  KEY `SubFrameElement_Ref` (`SubFrameElement_Ref`),
  CONSTRAINT `ferelation_ibfk_1` FOREIGN KEY (`FrameRelation_Ref`) REFERENCES `framerelation` (`ID`),
  CONSTRAINT `ferelation_ibfk_2` FOREIGN KEY (`SuperFrameElement_Ref`) REFERENCES `frameelement` (`ID`),
  CONSTRAINT `ferelation_ibfk_3` FOREIGN KEY (`SubFrameElement_Ref`) REFERENCES `frameelement` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table FERelationType
# ------------------------------------------------------------

DROP TABLE IF EXISTS `FERelationType`;

CREATE TABLE `FERelationType` (
  `ID` int(11) NOT NULL,
  `Name` varchar(80) DEFAULT NULL,
  `Description` text,
  `RelationType_Ref` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `RelationType_Ref` (`RelationType_Ref`),
  CONSTRAINT `ferelationtype_ibfk_1` FOREIGN KEY (`RelationType_Ref`) REFERENCES `relationtype` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Frame
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Frame`;

CREATE TABLE `Frame` (
  `Name` varchar(80) NOT NULL,
  `Definition` text,
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SymbolicRep` varchar(80) DEFAULT NULL,
  `Image` mediumblob,
  `CreatedBy` varchar(40) NOT NULL,
  `CreatedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Name` (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table FrameElement
# ------------------------------------------------------------

DROP TABLE IF EXISTS `FrameElement`;

CREATE TABLE `FrameElement` (
  `ID` int(11) NOT NULL,
  `Name` varchar(80) NOT NULL,
  `Definition` text,
  `Abbrev` varchar(40) DEFAULT NULL,
  `Frame_Ref` int(11) NOT NULL,
  `SemRoleRank` int(11) DEFAULT NULL,
  `Type` enum('Core','Peripheral','Extra-Thematic','Core-Unexpressed') DEFAULT NULL,
  `Core` enum('Y','N') DEFAULT 'N',
  `CreatedBy` varchar(40) NOT NULL,
  `CreatedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `Frame_Index` (`Frame_Ref`),
  CONSTRAINT `frameelement_ibfk_1` FOREIGN KEY (`Frame_Ref`) REFERENCES `frame` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table FrameRelation
# ------------------------------------------------------------

DROP TABLE IF EXISTS `FrameRelation`;

CREATE TABLE `FrameRelation` (
  `ID` int(11) NOT NULL,
  `Name` varchar(80) NOT NULL,
  `Profiled` int(11) DEFAULT NULL,
  `RelationType_Ref` int(11) NOT NULL,
  `SuperFrame_Ref` int(11) NOT NULL,
  `SubFrame_Ref` int(11) DEFAULT NULL,
  `CreatedBy` varchar(40) NOT NULL,
  `CreatedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `RelationType_Ref` (`RelationType_Ref`),
  KEY `SuperFrame_Ref` (`SuperFrame_Ref`),
  KEY `SubFrame_Ref` (`SubFrame_Ref`),
  CONSTRAINT `framerelation_ibfk_1` FOREIGN KEY (`RelationType_Ref`) REFERENCES `relationtype` (`ID`),
  CONSTRAINT `framerelation_ibfk_2` FOREIGN KEY (`SuperFrame_Ref`) REFERENCES `frame` (`ID`),
  CONSTRAINT `framerelation_ibfk_3` FOREIGN KEY (`SubFrame_Ref`) REFERENCES `frame` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Genre
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Genre`;

CREATE TABLE `Genre` (
  `ID` int(11) NOT NULL,
  `Name` varchar(80) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table InstantiationType
# ------------------------------------------------------------

DROP TABLE IF EXISTS `InstantiationType`;

CREATE TABLE `InstantiationType` (
  `ID` int(11) NOT NULL,
  `Name` varchar(40) DEFAULT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `Display` enum('Y','N') DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Label
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Label`;

CREATE TABLE `Label` (
  `ID` int(11) NOT NULL,
  `LabelType_Ref` int(11) NOT NULL,
  `Layer_Ref` int(11) NOT NULL,
  `InstantiationType_Ref` int(11) NOT NULL,
  `StartChar` int(11) DEFAULT NULL,
  `EndChar` int(11) DEFAULT NULL,
  `Multi` enum('Y','N') DEFAULT NULL,
  `CreatedBy` varchar(40) NOT NULL,
  `CreatedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `LabelType_Ref` (`LabelType_Ref`),
  KEY `InstantiationType_Ref` (`InstantiationType_Ref`),
  KEY `Layer_Ref` (`Layer_Ref`),
  CONSTRAINT `label_ibfk_3` FOREIGN KEY (`Layer_Ref`) REFERENCES `layer` (`ID`),
  CONSTRAINT `label_ibfk_1` FOREIGN KEY (`LabelType_Ref`) REFERENCES `labeltype` (`ID`),
  CONSTRAINT `label_ibfk_2` FOREIGN KEY (`InstantiationType_Ref`) REFERENCES `instantiationtype` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table LabelPos
# ------------------------------------------------------------

DROP TABLE IF EXISTS `LabelPos`;

CREATE TABLE `LabelPos` (
  `ID` int(11) NOT NULL,
  `Label_Ref` int(11) NOT NULL,
  `StartChar` int(11) DEFAULT NULL,
  `EndChar` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `Label_Ref` (`Label_Ref`),
  CONSTRAINT `labelpos_ibfk_1` FOREIGN KEY (`Label_Ref`) REFERENCES `label` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table LabelType
# ------------------------------------------------------------

DROP TABLE IF EXISTS `LabelType`;

CREATE TABLE `LabelType` (
  `ID` int(11) NOT NULL,
  `FgColorS_Ref` int(11) NOT NULL,
  `BgColorS_Ref` int(11) NOT NULL,
  `FgColorP_Ref` int(11) NOT NULL,
  `BgColorP_Ref` int(11) NOT NULL,
  `Keystroke` varchar(40) DEFAULT NULL,
  `LayerType_Ref` int(11) NOT NULL,
  `FrameElement_Ref` int(11) DEFAULT NULL,
  `ConstructElement_Ref` int(11) DEFAULT NULL,
  `MiscLabel_Ref` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FgColorS_Ref` (`FgColorS_Ref`),
  KEY `BgColorS_Ref` (`BgColorS_Ref`),
  KEY `FgColorP_Ref` (`FgColorP_Ref`),
  KEY `BgColorP_Ref` (`BgColorP_Ref`),
  KEY `LayerType_Ref` (`LayerType_Ref`),
  KEY `FrameElement_Ref` (`FrameElement_Ref`),
  KEY `MiscLabel_Ref` (`MiscLabel_Ref`),
  KEY `ConstructElement_Ref` (`ConstructElement_Ref`),
  CONSTRAINT `labeltype_ibfk_9` FOREIGN KEY (`ConstructElement_Ref`) REFERENCES `constructelement` (`ID`),
  CONSTRAINT `labeltype_ibfk_1` FOREIGN KEY (`FgColorS_Ref`) REFERENCES `color` (`ID`),
  CONSTRAINT `labeltype_ibfk_2` FOREIGN KEY (`BgColorS_Ref`) REFERENCES `color` (`ID`),
  CONSTRAINT `labeltype_ibfk_3` FOREIGN KEY (`FgColorP_Ref`) REFERENCES `color` (`ID`),
  CONSTRAINT `labeltype_ibfk_4` FOREIGN KEY (`BgColorP_Ref`) REFERENCES `color` (`ID`),
  CONSTRAINT `labeltype_ibfk_5` FOREIGN KEY (`LayerType_Ref`) REFERENCES `layertype` (`ID`),
  CONSTRAINT `labeltype_ibfk_6` FOREIGN KEY (`FrameElement_Ref`) REFERENCES `frameelement` (`ID`),
  CONSTRAINT `labeltype_ibfk_8` FOREIGN KEY (`MiscLabel_Ref`) REFERENCES `misclabel` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Layer
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Layer`;

CREATE TABLE `Layer` (
  `ID` int(11) NOT NULL,
  `AnnotationSet_Ref` int(11) NOT NULL,
  `LayerType_Ref` int(11) NOT NULL,
  `Rank` int(11) DEFAULT NULL,
  `CreatedBy` varchar(40) NOT NULL,
  `CreatedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `AnnotationSet_Ref` (`AnnotationSet_Ref`),
  KEY `LayerType_Ref` (`LayerType_Ref`),
  CONSTRAINT `layer_ibfk_2` FOREIGN KEY (`LayerType_Ref`) REFERENCES `layertype` (`ID`),
  CONSTRAINT `layer_ibfk_1` FOREIGN KEY (`AnnotationSet_Ref`) REFERENCES `annotationset` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table LayerType
# ------------------------------------------------------------

DROP TABLE IF EXISTS `LayerType`;

CREATE TABLE `LayerType` (
  `ID` int(11) NOT NULL,
  `Name` varchar(80) NOT NULL,
  `Definition` varchar(255) DEFAULT NULL,
  `AllowsApositional` int(11) DEFAULT NULL,
  `LGroup` varchar(20) DEFAULT NULL,
  `IsAnnotation` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Lemma
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Lemma`;

CREATE TABLE `Lemma` (
  `ID` int(11) NOT NULL,
  `CreatedBy` varchar(40) NOT NULL,
  `CreatedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `PartOfSpch_Ref` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `PartOfSpch_Ref` (`PartOfSpch_Ref`),
  CONSTRAINT `lemma_ibfk_1` FOREIGN KEY (`PartOfSpch_Ref`) REFERENCES `partofspch` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Lexeme
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Lexeme`;

CREATE TABLE `Lexeme` (
  `ID` int(11) NOT NULL,
  `NAME` varchar(80) NOT NULL,
  `CreatedBy` varchar(40) NOT NULL,
  `CreatedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `PartOfSpch_Ref` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `PartOfSpch_ref` (`PartOfSpch_Ref`),
  CONSTRAINT `lexeme_ibfk_1` FOREIGN KEY (`PartOfSpch_Ref`) REFERENCES `partofspch` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table LexemeEntry
# ------------------------------------------------------------

DROP TABLE IF EXISTS `LexemeEntry`;

CREATE TABLE `LexemeEntry` (
  `ID` int(11) NOT NULL,
  `CreatedBy` varchar(40) NOT NULL,
  `CreatedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Lexeme_Ref` int(11) NOT NULL,
  `Lemma_Ref` int(11) NOT NULL,
  `BreakBefore` tinyint(3) unsigned DEFAULT NULL,
  `Headword` tinyint(3) unsigned DEFAULT NULL,
  `LexemeOrder` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `Lexeme_Ref` (`Lexeme_Ref`),
  KEY `Lemma_Ref` (`Lemma_Ref`),
  CONSTRAINT `lexemeentry_ibfk_2` FOREIGN KEY (`Lemma_Ref`) REFERENCES `lemma` (`ID`),
  CONSTRAINT `lexemeentry_ibfk_1` FOREIGN KEY (`Lexeme_Ref`) REFERENCES `lexeme` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table LexUnit
# ------------------------------------------------------------

DROP TABLE IF EXISTS `LexUnit`;

CREATE TABLE `LexUnit` (
  `ID` int(11) NOT NULL,
  `Name` varchar(80) NOT NULL,
  `Frame_Ref` int(11) NOT NULL,
  `Lemma_Ref` int(11) NOT NULL,
  `SenseDesc` varchar(255) DEFAULT NULL,
  `ImportNum` int(11) DEFAULT NULL,
  `IncorporatedFE` int(11) DEFAULT NULL,
  `CreatedBy` varchar(40) NOT NULL,
  `CreatedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `Frame_Ref` (`Frame_Ref`),
  KEY `Lemma_Ref` (`Lemma_Ref`),
  CONSTRAINT `lexunit_ibfk_2` FOREIGN KEY (`Lemma_Ref`) REFERENCES `lemma` (`ID`),
  CONSTRAINT `lexunit_ibfk_1` FOREIGN KEY (`Frame_Ref`) REFERENCES `frame` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table MetaRelation
# ------------------------------------------------------------

DROP TABLE IF EXISTS `MetaRelation`;

CREATE TABLE `MetaRelation` (
  `ID` int(11) NOT NULL,
  `Directed` int(11) DEFAULT NULL,
  `A_RelationRef` int(11) NOT NULL,
  `B_RelationRef` int(11) NOT NULL,
  `MetaRelationType_Ref` int(11) NOT NULL,
  `CreatedBy` varchar(40) NOT NULL,
  `CreatedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `MetaRelationType_Ref` (`MetaRelationType_Ref`),
  CONSTRAINT `metarelation_ibfk_1` FOREIGN KEY (`MetaRelationType_Ref`) REFERENCES `metarelationtype` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table MetaRelationType
# ------------------------------------------------------------

DROP TABLE IF EXISTS `MetaRelationType`;

CREATE TABLE `MetaRelationType` (
  `ID` int(11) NOT NULL,
  `Name` varchar(80) NOT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `A_RelationName` varchar(80) NOT NULL,
  `B_RelationName` varchar(80) NOT NULL,
  `RelationType_Ref` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `RelationType_Ref` (`RelationType_Ref`),
  CONSTRAINT `metarelationtype_ibfk_1` FOREIGN KEY (`RelationType_Ref`) REFERENCES `relationtype` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table MiscLabel
# ------------------------------------------------------------

DROP TABLE IF EXISTS `MiscLabel`;

CREATE TABLE `MiscLabel` (
  `ID` int(11) NOT NULL,
  `Name` varchar(80) NOT NULL,
  `Definition` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Note
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Note`;

CREATE TABLE `Note` (
  `ID` int(11) NOT NULL,
  `Text` varchar(255) NOT NULL,
  `NoteType_Ref` int(11) NOT NULL,
  `CreatedBy` varchar(40) NOT NULL,
  `CreatedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `NoteType_Ref` (`NoteType_Ref`),
  CONSTRAINT `note_ibfk_1` FOREIGN KEY (`NoteType_Ref`) REFERENCES `notetype` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table NoteLink
# ------------------------------------------------------------

DROP TABLE IF EXISTS `NoteLink`;

CREATE TABLE `NoteLink` (
  `ID` int(11) NOT NULL,
  `Note_Ref` int(11) DEFAULT NULL,
  `Frame_Ref` int(11) DEFAULT NULL,
  `FrameElement_Ref` int(11) DEFAULT NULL,
  `Construction_Ref` int(11) DEFAULT NULL,
  `ConstructionElement_Ref` int(11) DEFAULT NULL,
  `LexUnit_Ref` int(11) DEFAULT NULL,
  `Lemma_Ref` int(11) DEFAULT NULL,
  `Lexeme_Ref` int(11) DEFAULT NULL,
  `SemanticType_Ref` int(11) DEFAULT NULL,
  `SubCorpus_Ref` int(11) DEFAULT NULL,
  `Sentence_Ref` int(11) DEFAULT NULL,
  `Corpus_Ref` int(11) DEFAULT NULL,
  `Document_Ref` int(11) DEFAULT NULL,
  `Paragraph_Ref` int(11) DEFAULT NULL,
  `AnnotationSet_Ref` int(11) DEFAULT NULL,
  `Layer_Ref` int(11) DEFAULT NULL,
  `Label_Ref` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `Note_Ref` (`Note_Ref`),
  KEY `Frame_Ref` (`Frame_Ref`),
  KEY `FrameElement_Ref` (`FrameElement_Ref`),
  KEY `Construction_Ref` (`Construction_Ref`),
  KEY `ConstructionElement_Ref` (`ConstructionElement_Ref`),
  KEY `LexUnit_Ref` (`LexUnit_Ref`),
  KEY `Lemma_Ref` (`Lemma_Ref`),
  KEY `Lexeme_Ref` (`Lexeme_Ref`),
  KEY `SemanticType_Ref` (`SemanticType_Ref`),
  KEY `SubCorpus_Ref` (`SubCorpus_Ref`),
  KEY `Sentence_Ref` (`Sentence_Ref`),
  KEY `Corpus_Ref` (`Corpus_Ref`),
  KEY `Document_Ref` (`Document_Ref`),
  KEY `Paragraph_Ref` (`Paragraph_Ref`),
  KEY `AnnotationSet_Ref` (`AnnotationSet_Ref`),
  KEY `Label_Ref` (`Label_Ref`),
  KEY `Layer_Ref` (`Layer_Ref`),
  CONSTRAINT `notelink_ibfk_17` FOREIGN KEY (`Layer_Ref`) REFERENCES `layer` (`ID`),
  CONSTRAINT `notelink_ibfk_1` FOREIGN KEY (`Note_Ref`) REFERENCES `note` (`ID`),
  CONSTRAINT `notelink_ibfk_10` FOREIGN KEY (`SubCorpus_Ref`) REFERENCES `subcorpus` (`ID`),
  CONSTRAINT `notelink_ibfk_11` FOREIGN KEY (`Sentence_Ref`) REFERENCES `sentence` (`ID`),
  CONSTRAINT `notelink_ibfk_12` FOREIGN KEY (`Corpus_Ref`) REFERENCES `corpus` (`ID`),
  CONSTRAINT `notelink_ibfk_13` FOREIGN KEY (`Document_Ref`) REFERENCES `document` (`ID`),
  CONSTRAINT `notelink_ibfk_14` FOREIGN KEY (`Paragraph_Ref`) REFERENCES `paragraph` (`ID`),
  CONSTRAINT `notelink_ibfk_15` FOREIGN KEY (`AnnotationSet_Ref`) REFERENCES `annotationset` (`ID`),
  CONSTRAINT `notelink_ibfk_16` FOREIGN KEY (`Label_Ref`) REFERENCES `label` (`ID`),
  CONSTRAINT `notelink_ibfk_2` FOREIGN KEY (`Frame_Ref`) REFERENCES `frame` (`ID`),
  CONSTRAINT `notelink_ibfk_3` FOREIGN KEY (`FrameElement_Ref`) REFERENCES `frameelement` (`ID`),
  CONSTRAINT `notelink_ibfk_4` FOREIGN KEY (`Construction_Ref`) REFERENCES `construction` (`ID`),
  CONSTRAINT `notelink_ibfk_5` FOREIGN KEY (`ConstructionElement_Ref`) REFERENCES `constructelement` (`ID`),
  CONSTRAINT `notelink_ibfk_6` FOREIGN KEY (`LexUnit_Ref`) REFERENCES `lexunit` (`ID`),
  CONSTRAINT `notelink_ibfk_7` FOREIGN KEY (`Lemma_Ref`) REFERENCES `lemma` (`ID`),
  CONSTRAINT `notelink_ibfk_8` FOREIGN KEY (`Lexeme_Ref`) REFERENCES `lexeme` (`ID`),
  CONSTRAINT `notelink_ibfk_9` FOREIGN KEY (`SemanticType_Ref`) REFERENCES `semantictype` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table NoteType
# ------------------------------------------------------------

DROP TABLE IF EXISTS `NoteType`;

CREATE TABLE `NoteType` (
  `ID` int(11) NOT NULL,
  `Name` varchar(80) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Paragraph
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Paragraph`;

CREATE TABLE `Paragraph` (
  `ID` int(11) NOT NULL,
  `DocumentOrder` int(10) unsigned DEFAULT NULL,
  `Document_Ref` int(11) NOT NULL,
  `CreatedBy` varchar(40) NOT NULL,
  `CreatedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `Document_Ref` (`Document_Ref`),
  CONSTRAINT `paragraph_ibfk_1` FOREIGN KEY (`Document_Ref`) REFERENCES `document` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table PartOfSpch
# ------------------------------------------------------------

DROP TABLE IF EXISTS `PartOfSpch`;

CREATE TABLE `PartOfSpch` (
  `ID` int(11) NOT NULL,
  `Name` varchar(80) NOT NULL DEFAULT '',
  `Definition` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Principals
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Principals`;

CREATE TABLE `Principals` (
  `User` varchar(40) NOT NULL DEFAULT '',
  `Password` varchar(255) DEFAULT '',
  `AnonID` int(5) DEFAULT NULL,
  `OldPassword` varchar(255) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



# Dump of table ReFraming
# ------------------------------------------------------------

DROP TABLE IF EXISTS `ReFraming`;

CREATE TABLE `ReFraming` (
  `ID` int(11) NOT NULL,
  `FrameRelation_Ref` int(11) NOT NULL,
  `AnnotationSet_Ref` int(11) NOT NULL,
  `LexUnit_Ref` int(11) NOT NULL,
  `CreatedBy` varchar(40) NOT NULL,
  `CreatedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `FrameRelation_Ref` (`FrameRelation_Ref`),
  KEY `AnnotationSet_Ref` (`AnnotationSet_Ref`),
  KEY `LexUnit_Ref` (`LexUnit_Ref`),
  CONSTRAINT `reframing_ibfk_3` FOREIGN KEY (`LexUnit_Ref`) REFERENCES `lexunit` (`ID`),
  CONSTRAINT `reframing_ibfk_1` FOREIGN KEY (`FrameRelation_Ref`) REFERENCES `framerelation` (`ID`),
  CONSTRAINT `reframing_ibfk_2` FOREIGN KEY (`AnnotationSet_Ref`) REFERENCES `annotationset` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table RelationType
# ------------------------------------------------------------

DROP TABLE IF EXISTS `RelationType`;

CREATE TABLE `RelationType` (
  `ID` int(11) NOT NULL,
  `Name` varchar(80) NOT NULL,
  `Description` text,
  `SuperFrameName` varchar(80) NOT NULL,
  `SubFrameName` varchar(80) NOT NULL,
  `Profiles` int(11) DEFAULT NULL,
  `Complete` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table RevisedLus
# ------------------------------------------------------------

DROP TABLE IF EXISTS `RevisedLus`;

CREATE TABLE `RevisedLus` (
  `ID` int(11) NOT NULL,
  `DeletedLuID` int(11) DEFAULT NULL,
  `CreatedBy` varchar(40) NOT NULL,
  `CreatedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `LexUnitID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `DeletedLuID` (`DeletedLuID`),
  KEY `LexUnitID` (`LexUnitID`),
  CONSTRAINT `revisedlus_ibfk_2` FOREIGN KEY (`LexUnitID`) REFERENCES `lexunit` (`ID`),
  CONSTRAINT `revisedlus_ibfk_1` FOREIGN KEY (`DeletedLuID`) REFERENCES `lexunit` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Roles
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Roles`;

CREATE TABLE `Roles` (
  `User` varchar(40) NOT NULL DEFAULT '',
  `Role` varchar(40) NOT NULL DEFAULT 'read',
  `RoleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`RoleID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table SemanticType
# ------------------------------------------------------------

DROP TABLE IF EXISTS `SemanticType`;

CREATE TABLE `SemanticType` (
  `ID` int(11) NOT NULL,
  `Name` varchar(80) NOT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `Abbrev` varchar(20) DEFAULT NULL,
  `CreatedBy` varchar(40) NOT NULL,
  `CreatedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table SemanticType_Frame
# ------------------------------------------------------------

DROP TABLE IF EXISTS `SemanticType_Frame`;

CREATE TABLE `SemanticType_Frame` (
  `SemanticType_Ref` int(11) NOT NULL DEFAULT '0',
  `Frame_Ref` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`SemanticType_Ref`,`Frame_Ref`),
  KEY `Frame_Ref` (`Frame_Ref`),
  CONSTRAINT `semantictype_frame_ibfk_2` FOREIGN KEY (`Frame_Ref`) REFERENCES `frame` (`ID`),
  CONSTRAINT `semantictype_frame_ibfk_1` FOREIGN KEY (`SemanticType_Ref`) REFERENCES `semantictype` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table SemanticType_FrameElement
# ------------------------------------------------------------

DROP TABLE IF EXISTS `SemanticType_FrameElement`;

CREATE TABLE `SemanticType_FrameElement` (
  `FrameElement_Ref` int(11) NOT NULL,
  `SemanticType_Ref` int(11) NOT NULL,
  PRIMARY KEY (`FrameElement_Ref`,`SemanticType_Ref`),
  KEY `SemanticType_Ref` (`SemanticType_Ref`),
  CONSTRAINT `semantictype_frameelement_ibfk_2` FOREIGN KEY (`SemanticType_Ref`) REFERENCES `semantictype` (`ID`),
  CONSTRAINT `semantictype_frameelement_ibfk_1` FOREIGN KEY (`FrameElement_Ref`) REFERENCES `frameelement` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table SemanticType_LexUnit
# ------------------------------------------------------------

DROP TABLE IF EXISTS `SemanticType_LexUnit`;

CREATE TABLE `SemanticType_LexUnit` (
  `SemanticType_Ref` int(11) NOT NULL DEFAULT '0',
  `LexUnit_Ref` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`SemanticType_Ref`,`LexUnit_Ref`),
  KEY `LexUnit_Ref` (`LexUnit_Ref`),
  CONSTRAINT `semantictype_lexunit_ibfk_2` FOREIGN KEY (`LexUnit_Ref`) REFERENCES `lexunit` (`ID`),
  CONSTRAINT `semantictype_lexunit_ibfk_1` FOREIGN KEY (`SemanticType_Ref`) REFERENCES `semantictype` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Sentence
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Sentence`;

CREATE TABLE `Sentence` (
  `ID` int(11) NOT NULL,
  `Text` varchar(500) NOT NULL,
  `Paragraph_Ref` int(11) DEFAULT NULL,
  `AbsolutePos` int(11) DEFAULT NULL,
  `ParagraphOrder` int(11) DEFAULT NULL,
  `CreatedBy` varchar(40) NOT NULL,
  `CreatedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ExternalID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `Paragraph_Ref` (`Paragraph_Ref`),
  CONSTRAINT `sentence_ibfk_1` FOREIGN KEY (`Paragraph_Ref`) REFERENCES `paragraph` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Sequence
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Sequence`;

CREATE TABLE `Sequence` (
  `Name` varchar(80) NOT NULL,
  `Value` int(11) NOT NULL,
  PRIMARY KEY (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Status
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Status`;

CREATE TABLE `Status` (
  `ID` int(11) NOT NULL,
  `Text` varchar(255) DEFAULT NULL,
  `LexUnit_Ref` int(11) NOT NULL,
  `StatusType_Ref` int(11) NOT NULL,
  `CreatedBy` varchar(40) NOT NULL,
  `CreatedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `LexUnit_Ref` (`LexUnit_Ref`),
  KEY `StatusType_Ref` (`StatusType_Ref`),
  CONSTRAINT `status_ibfk_2` FOREIGN KEY (`StatusType_Ref`) REFERENCES `statustype` (`ID`),
  CONSTRAINT `status_ibfk_1` FOREIGN KEY (`LexUnit_Ref`) REFERENCES `lexunit` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table StatusType
# ------------------------------------------------------------

DROP TABLE IF EXISTS `StatusType`;

CREATE TABLE `StatusType` (
  `ID` int(11) NOT NULL,
  `Name` varchar(40) NOT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `Icon` blob,
  `Rank` int(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table STInherit
# ------------------------------------------------------------

DROP TABLE IF EXISTS `STInherit`;

CREATE TABLE `STInherit` (
  `ParentST_Ref` int(11) NOT NULL DEFAULT '0',
  `ChildST_Ref` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ParentST_Ref`,`ChildST_Ref`),
  KEY `ChildST_Ref` (`ChildST_Ref`),
  CONSTRAINT `stinherit_ibfk_2` FOREIGN KEY (`ChildST_Ref`) REFERENCES `semantictype` (`ID`),
  CONSTRAINT `stinherit_ibfk_1` FOREIGN KEY (`ParentST_Ref`) REFERENCES `semantictype` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table SubCorpus
# ------------------------------------------------------------

DROP TABLE IF EXISTS `SubCorpus`;

CREATE TABLE `SubCorpus` (
  `ID` int(11) NOT NULL,
  `Name` varchar(80) NOT NULL,
  `LexUnit_Ref` int(11) NOT NULL,
  `Construction_Ref` int(11) DEFAULT NULL,
  `Rank` int(11) DEFAULT NULL,
  `CreatedBy` varchar(40) NOT NULL,
  `CreatedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `LexUnit_Ref` (`LexUnit_Ref`),
  KEY `Construction_Ref` (`Construction_Ref`),
  CONSTRAINT `subcorpus_ibfk_1` FOREIGN KEY (`LexUnit_Ref`) REFERENCES `lexunit` (`ID`),
  CONSTRAINT `subcorpus_ibfk_2` FOREIGN KEY (`Construction_Ref`) REFERENCES `construction` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table UnidicEntry
# ------------------------------------------------------------

DROP TABLE IF EXISTS `UnidicEntry`;

CREATE TABLE `UnidicEntry` (
  `Form` varchar(255) DEFAULT NULL,
  `B` int(11) unsigned DEFAULT NULL,
  `C` int(11) unsigned DEFAULT NULL,
  `D` int(11) unsigned DEFAULT NULL,
  `品詞大分類` varchar(255) DEFAULT NULL,
  `品詞中分類` varchar(255) DEFAULT NULL,
  `品詞小分類` varchar(255) DEFAULT NULL,
  `品詞細分類` varchar(255) DEFAULT NULL,
  `活用型` varchar(255) DEFAULT NULL,
  `活用形` varchar(255) DEFAULT NULL,
  `語彙素読み` varchar(255) DEFAULT NULL,
  `語彙素` varchar(255) DEFAULT NULL,
  `書字形出現形` varchar(255) DEFAULT NULL,
  `発音形出現形` varchar(255) DEFAULT NULL,
  `書字形基本形` varchar(255) DEFAULT NULL,
  `発音形基本形` varchar(255) DEFAULT NULL,
  `語種` varchar(255) DEFAULT NULL,
  `語頭変化型` varchar(255) DEFAULT NULL,
  `語頭変化形` varchar(255) DEFAULT NULL,
  `語末変化型` varchar(255) DEFAULT NULL,
  `語末変化形` varchar(255) DEFAULT NULL,
  KEY `書字形基本形` (`書字形基本形`),
  KEY `Form` (`Form`),
  KEY `書字形基本形_2` (`書字形基本形`,`品詞大分類`),
  KEY `語彙素` (`語彙素`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table WordForm
# ------------------------------------------------------------

DROP TABLE IF EXISTS `WordForm`;

CREATE TABLE `WordForm` (
  `ID` int(11) NOT NULL,
  `Form` varchar(80) NOT NULL,
  `CreatedBy` varchar(40) NOT NULL,
  `CreatedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Lexeme_ref` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `Lexeme_ref` (`Lexeme_ref`),
  CONSTRAINT `wordform_ibfk_1` FOREIGN KEY (`Lexeme_ref`) REFERENCES `lexeme` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

# Initial insert 
INSERT INTO jfnwat.`Principals` (`User`, `OldPassword`, `AnonID`) SELECT * FROM jfn02.`Principals`;
INSERT INTO jfnwat.`Roles` (`User`, `Role`) SELECT * FROM jfn02.`Roles`;
INSERT INTO jfnwat.`UnidicEntry` (`Form`, `B`, `C`, `D`, `品詞大分類`, `品詞中分類`, `品詞小分類`, `品詞細分類`, `活用型`, `活用形`, `語彙素読み`, `語彙素`, `書字形出現形`, `発音形出現形`, `書字形基本形`, `発音形基本形`, `語種`, `語頭変化型`, `語頭変化形`, `語末変化型`, `語末変化形`) SELECT * FROM `jfn02`.`UnidicEntry`;
