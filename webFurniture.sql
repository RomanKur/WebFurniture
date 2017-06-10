-- phpMyAdmin SQL Dump
-- version 4.2.12deb2+deb8u2
-- http://www.phpmyadmin.net
--
-- Хост: localhost
-- Время создания: Май 24 2017 г., 15:14
-- Версия сервера: 5.5.53-0+deb8u1
-- Версия PHP: 5.6.29-0+deb8u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- База данных: `webFurniture`
--

-- --------------------------------------------------------

--
-- Структура таблицы `DONEWORK`
--

CREATE TABLE IF NOT EXISTS `DONEWORK` (
`ID` bigint(20) NOT NULL,
  `DONE` int(11) DEFAULT NULL,
  `MODELID` bigint(20) DEFAULT NULL,
  `MONTH` int(11) DEFAULT NULL,
  `ORDERID` bigint(20) DEFAULT NULL,
  `PARTID` bigint(20) DEFAULT NULL,
  `PRICE` int(11) DEFAULT NULL,
  `WEEK` int(11) DEFAULT NULL,
  `WORKERID` bigint(20) DEFAULT NULL,
  `YEAR` int(11) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=latin1;

--
-- Дамп данных таблицы `DONEWORK`
--

INSERT INTO `DONEWORK` (`ID`, `DONE`, `MODELID`, `MONTH`, `ORDERID`, `PARTID`, `PRICE`, `WEEK`, `WORKERID`, `YEAR`) VALUES
(1, 2, 1, 5, 1, 1, 40, 21, 5, 2017),
(4, 2, 1, 5, 1, 2, 40, 21, 5, 2017),
(38, 1, 1, 5, 1, 2, 40, 21, 5, 2017),
(39, 1, 1, 5, 1, 1, 40, 21, 5, 2017);

-- --------------------------------------------------------

--
-- Структура таблицы `MODEL`
--

CREATE TABLE IF NOT EXISTS `MODEL` (
`ID` bigint(20) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Дамп данных таблицы `MODEL`
--

INSERT INTO `MODEL` (`ID`, `NAME`) VALUES
(1, 'chair');

-- --------------------------------------------------------

--
-- Структура таблицы `MODEL_COUNT`
--

CREATE TABLE IF NOT EXISTS `MODEL_COUNT` (
  `OrderFurniture_ID` bigint(20) DEFAULT NULL,
  `MODELS` int(11) DEFAULT NULL,
  `MODELS_KEY` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Дамп данных таблицы `MODEL_COUNT`
--

INSERT INTO `MODEL_COUNT` (`OrderFurniture_ID`, `MODELS`, `MODELS_KEY`) VALUES
(1, 5, 1),
(2, 1, 1);

-- --------------------------------------------------------

--
-- Структура таблицы `MODEL_PART`
--

CREATE TABLE IF NOT EXISTS `MODEL_PART` (
  `Model_ID` bigint(20) NOT NULL,
  `parts_ID` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Дамп данных таблицы `MODEL_PART`
--

INSERT INTO `MODEL_PART` (`Model_ID`, `parts_ID`) VALUES
(1, 1),
(1, 2),
(1, 3);

-- --------------------------------------------------------

--
-- Структура таблицы `ORDERDATE`
--

CREATE TABLE IF NOT EXISTS `ORDERDATE` (
`ID` bigint(20) NOT NULL,
  `MONTH` int(11) DEFAULT NULL,
  `WEEK` int(11) DEFAULT NULL,
  `YEAR` int(11) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Дамп данных таблицы `ORDERDATE`
--

INSERT INTO `ORDERDATE` (`ID`, `MONTH`, `WEEK`, `YEAR`) VALUES
(1, 5, 21, 2017),
(2, 4, 14, 2017);

-- --------------------------------------------------------

--
-- Структура таблицы `ORDERFURNITURE`
--

CREATE TABLE IF NOT EXISTS `ORDERFURNITURE` (
`ID` bigint(20) NOT NULL,
  `CREATEORDERFURNITURE` datetime DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `ORDERDATE_ID` bigint(20) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Дамп данных таблицы `ORDERFURNITURE`
--

INSERT INTO `ORDERFURNITURE` (`ID`, `CREATEORDERFURNITURE`, `NAME`, `ORDERDATE_ID`) VALUES
(1, NULL, 'chairs', 1),
(2, NULL, 'order', 2);

-- --------------------------------------------------------

--
-- Структура таблицы `PART`
--

CREATE TABLE IF NOT EXISTS `PART` (
`ID` bigint(20) NOT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `DURATION` int(11) DEFAULT NULL,
  `PRICE` int(11) DEFAULT NULL,
  `SERIAL` varchar(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Дамп данных таблицы `PART`
--

INSERT INTO `PART` (`ID`, `DESCRIPTION`, `DURATION`, `PRICE`, `SERIAL`) VALUES
(1, 'chair seat', 40, 40, '51fd61'),
(2, '4 chair legs', 40, 40, '1gf651'),
(3, 'chair backrest', 20, 20, '1dg1e1r');

-- --------------------------------------------------------

--
-- Структура таблицы `PERSON`
--

CREATE TABLE IF NOT EXISTS `PERSON` (
`ID` bigint(20) NOT NULL,
  `DTYPE` varchar(31) DEFAULT NULL,
  `FIRSTNAME` varchar(255) DEFAULT NULL,
  `ISIKUKOOD` varchar(255) DEFAULT NULL,
  `LASTNAME` varchar(255) DEFAULT NULL,
  `MAIL` varchar(255) DEFAULT NULL,
  `TELEPHON` varchar(255) DEFAULT NULL,
  `LOGIN` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `SALTS` varchar(255) DEFAULT NULL,
  `STATUS` varchar(255) DEFAULT NULL,
  `ADITIONALACCESS` varchar(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Дамп данных таблицы `PERSON`
--

INSERT INTO `PERSON` (`ID`, `DTYPE`, `FIRSTNAME`, `ISIKUKOOD`, `LASTNAME`, `MAIL`, `TELEPHON`, `LOGIN`, `PASSWORD`, `SALTS`, `STATUS`, `ADITIONALACCESS`) VALUES
(1, 'Worker', 'admin', '15616', 'admin', '561651', '561651', 'admin.admin', 'a3b31614a52d5fb072ed26b30d60d64a57489680a296c85a6a8b26f1b5b65aae', '259b90d4bb60c23a31f52b027f76e2ba', 'admin', NULL),
(3, 'Worker', 'staff', '651651', 'ivanov', '651651', '651651', 'staff.ivanov', 'c16f264d7da4aefe722decd609c4a2eb891e72215c92ae4b53a3f3bd8f411a10', '259b90d4bb60c23a31f52b027f76e2ba', 'Staff manager', ''),
(5, 'Worker', 'worker', '651651', 'ivanov', '651651', '651651', 'worker.ivanov', 'c16f264d7da4aefe722decd609c4a2eb891e72215c92ae4b53a3f3bd8f411a10', '259b90d4bb60c23a31f52b027f76e2ba', 'Worker', 'Desiner'),
(6, 'Worker', 'bookkeeper', '651651', 'ivanov', '651651', '651651', 'bookkeeper.ivanov', 'c16f264d7da4aefe722decd609c4a2eb891e72215c92ae4b53a3f3bd8f411a10', '259b90d4bb60c23a31f52b027f76e2ba', 'Bookkeeper', ''),
(7, 'Worker', 'desiner', '651651', 'ivanov', '651651', '651651', 'desiner.ivanov', 'c16f264d7da4aefe722decd609c4a2eb891e72215c92ae4b53a3f3bd8f411a10', '259b90d4bb60c23a31f52b027f76e2ba', 'Desiner', NULL),
(8, 'Worker', 'acceptor', '651651', 'ivanov', '651651', '651651', 'acceptor.ivanov', 'c16f264d7da4aefe722decd609c4a2eb891e72215c92ae4b53a3f3bd8f411a10', '259b90d4bb60c23a31f52b027f76e2ba', 'Order acceptor', NULL);

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `DONEWORK`
--
ALTER TABLE `DONEWORK`
 ADD PRIMARY KEY (`ID`);

--
-- Индексы таблицы `MODEL`
--
ALTER TABLE `MODEL`
 ADD PRIMARY KEY (`ID`);

--
-- Индексы таблицы `MODEL_COUNT`
--
ALTER TABLE `MODEL_COUNT`
 ADD KEY `FK_MODEL_COUNT_OrderFurniture_ID` (`OrderFurniture_ID`);

--
-- Индексы таблицы `MODEL_PART`
--
ALTER TABLE `MODEL_PART`
 ADD PRIMARY KEY (`Model_ID`,`parts_ID`), ADD KEY `FK_MODEL_PART_parts_ID` (`parts_ID`);

--
-- Индексы таблицы `ORDERDATE`
--
ALTER TABLE `ORDERDATE`
 ADD PRIMARY KEY (`ID`);

--
-- Индексы таблицы `ORDERFURNITURE`
--
ALTER TABLE `ORDERFURNITURE`
 ADD PRIMARY KEY (`ID`), ADD KEY `FK_ORDERFURNITURE_ORDERDATE_ID` (`ORDERDATE_ID`);

--
-- Индексы таблицы `PART`
--
ALTER TABLE `PART`
 ADD PRIMARY KEY (`ID`);

--
-- Индексы таблицы `PERSON`
--
ALTER TABLE `PERSON`
 ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `DONEWORK`
--
ALTER TABLE `DONEWORK`
MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=40;
--
-- AUTO_INCREMENT для таблицы `MODEL`
--
ALTER TABLE `MODEL`
MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT для таблицы `ORDERDATE`
--
ALTER TABLE `ORDERDATE`
MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT для таблицы `ORDERFURNITURE`
--
ALTER TABLE `ORDERFURNITURE`
MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT для таблицы `PART`
--
ALTER TABLE `PART`
MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT для таблицы `PERSON`
--
ALTER TABLE `PERSON`
MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=9;
--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `MODEL_COUNT`
--
ALTER TABLE `MODEL_COUNT`
ADD CONSTRAINT `FK_MODEL_COUNT_OrderFurniture_ID` FOREIGN KEY (`OrderFurniture_ID`) REFERENCES `ORDERFURNITURE` (`ID`);

--
-- Ограничения внешнего ключа таблицы `MODEL_PART`
--
ALTER TABLE `MODEL_PART`
ADD CONSTRAINT `FK_MODEL_PART_Model_ID` FOREIGN KEY (`Model_ID`) REFERENCES `MODEL` (`ID`),
ADD CONSTRAINT `FK_MODEL_PART_parts_ID` FOREIGN KEY (`parts_ID`) REFERENCES `PART` (`ID`);

--
-- Ограничения внешнего ключа таблицы `ORDERFURNITURE`
--
ALTER TABLE `ORDERFURNITURE`
ADD CONSTRAINT `FK_ORDERFURNITURE_ORDERDATE_ID` FOREIGN KEY (`ORDERDATE_ID`) REFERENCES `ORDERDATE` (`ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
