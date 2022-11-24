-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : lun. 21 nov. 2022 à 17:21
-- Version du serveur : 10.4.25-MariaDB
-- Version de PHP : 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `testpersonne`
--

-- --------------------------------------------------------

--
-- Structure de la table `film`
--

CREATE TABLE `Film` (
  `id` int(11) NOT NULL,
  `titre` varchar(40) NOT NULL,
  `id_rea` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `film`
--

INSERT INTO `Film` (`id`, `titre`, `id_rea`) VALUES
(1, 'Arche perdue', 1),
(2, 'Alien', 2),
(3, 'Temple Maudit', 1),
(4, 'Blade Runner', 2),
(5, 'Alien3', 4),
(6, 'Fight Club', 4),
(7, 'Orange Mecanique', 3);

-- --------------------------------------------------------

--
-- Structure de la table `personne`
--

CREATE TABLE `Personne` (
  `id` int(11) NOT NULL,
  `nom` varchar(40) NOT NULL,
  `prenom` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `personne`
--

INSERT INTO `Personne` (`id`, `nom`, `prenom`) VALUES
(1, 'Spielberg', 'Steven'),
(2, 'Scott', 'Ridley'),
(3, 'Kubrick', 'Stanley'),
(4, 'Fincher', 'David');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `film`
--
ALTER TABLE `Film`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_rea` (`id_rea`);

--
-- Index pour la table `personne`
--
ALTER TABLE `Personne`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `film`
--
ALTER TABLE `Film`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `personne`
--
ALTER TABLE `Personne`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `film`
--
ALTER TABLE `Film`
  ADD CONSTRAINT `film_ibfk_1` FOREIGN KEY (`id_rea`) REFERENCES `Personne` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
