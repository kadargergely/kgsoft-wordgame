package hu.unideb.kgsoft.scrabble;

import hu.unideb.kgsoft.scrabble.model.SavedGame;
import hu.unideb.kgsoft.scrabble.utils.Utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

public class SavedGameDAO {

    private Field[][] getFieldsBySavedGameId(int savedGameId)
            throws IOException {
        Field[][] fields = new Field[Gameboard.BOARD_SIZE][Gameboard.BOARD_SIZE];

        try (Connection connection = ConnectionFactory.getConnection()) {
            Statement st = connection.createStatement();
            ResultSet rset = st.executeQuery(String.format(
                    "select row_index, col_index, status, multiplier, tile_code, joker_tile_code "
                            + "from fields " + "where saved_game_id=%s",
                    Integer.toString(savedGameId)));

            while (rset.next()) {
                fields[rset.getInt(1)][rset.getInt(2)] = new Field(
                        Field.Status.valueOf(rset.getString(3)),
                        Field.Multiplier.valueOf(rset.getString(4)),
                        rset.getInt(5), rset.getInt(6));
            }
        } catch (SQLException e) {
            // TODO log - ez baj, ilyen nem kene legyen
            System.out.println(e.getMessage());
        }

        return fields;
    }
    
    private void insertFields(int savedGameId, Field[][] fields) throws IOException {
        try (Connection connection = ConnectionFactory.getConnection()) {
            Statement st = connection.createStatement();
            for (int i = 0; i < fields.length; i++) {
                for (int j = 0; j < fields[i].length; j++) {
                    st.executeUpdate(
                            String.format("insert into fields values (%s, %s, %s, '%s', '%s', %s, %s)", 
                                    Integer.toString(savedGameId), Integer.toString(i), Integer.toString(j),
                                    fields[i][j].getStatus(), fields[i][j].getMultiplier(),
                                    Integer.toString(fields[i][j].getTileCode()),
                                    Integer.toString(fields[i][j].getJokerTileCode())));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public SavedGame getSavedGameByUserName(String username)
            throws IOException, NoSavedGameException {
        SavedGame savedGame = null;

        try (Connection connection = ConnectionFactory.getConnection()) {
            Statement st = connection.createStatement();
            ResultSet rset = st.executeQuery(String.format(
                    "select * from saved_games where username='%s'", username));
            if (rset.next()) {
                Field[][] fields = getFieldsBySavedGameId(rset.getInt(1));
                BigDecimal[] playerTray = (BigDecimal[]) (((OracleResultSet) rset)
                        .getARRAY(3).getArray());
                BigDecimal[] computerTray = (BigDecimal[]) (((OracleResultSet) rset)
                        .getARRAY(4).getArray());                
                String[] lastWords = (String[]) rset.getArray(9).getArray();                
                BigDecimal[] bagRemaining = (BigDecimal[]) (((OracleResultSet) rset)
                        .getARRAY(11).getArray());
                savedGame = new SavedGame(fields, Utils.toIntArray(playerTray),
                        Utils.toIntArray(computerTray), rset.getInt(5),
                        rset.getInt(6), rset.getInt(7), rset.getInt(8),
                        lastWords, rset.getInt(10),
                        Utils.toIntArray(bagRemaining));
            } else {
                throw new NoSavedGameException();
            }
        } catch (SQLException e) {
            // TODO log - ez baj, ilyen nem kene legyen
            System.out.println(e.getMessage());
        }

        return savedGame;
    }

    public void newSavedGame(String username, SavedGame savedGame)
            throws IOException {
        try (Connection connection = ConnectionFactory.getConnection()) {            
            Statement st = connection.createStatement();
            ResultSet rset = st
                    .executeQuery(String.format("select count(username) "
                            + "from saved_games where username='%s'", username));
            Integer[] playerTrayInteger = Utils.toIntegerArray(savedGame
                    .getPlayerTray());
            Integer[] computerTrayInteger = Utils.toIntegerArray(savedGame
                    .getComputerTray());
            Integer[] bagRemainingInteger = Utils.toIntegerArray(savedGame
                    .getBagRemaining());
            if (rset.next() && rset.getInt(1) == 1) {
//                PreparedStatement pst = connection
//                        .prepareStatement("update saved_games "
//                                + "set player_tray=?," + "computer_tray=?,"
//                                + "tile_in_hand=?," + "players_turn=?,"
//                                + "player_score=?,"
//                                + "computer_score=?," + "last_words=?,"
//                                + "last_words_points=?,"
//                                + "bag_remaining=? " + "where username='"
//                                + username + "'");
                
                PreparedStatement pst = connection.prepareStatement(
                        "update saved_games "
                        + "set player_tray=?, computer_tray=?, tile_in_hand=?, players_turn=?, player_score=?, "
                        + "computer_score=?, last_words=?, last_words_points=?, bag_remaining=? where username='" 
                        + username + "'");

                ((OraclePreparedStatement) pst).setARRAY(1,
                        ((OracleConnection) connection).createARRAY("T_TRAY",
                                playerTrayInteger));
                ((OraclePreparedStatement) pst).setARRAY(2,
                        ((OracleConnection) connection).createARRAY("T_TRAY",
                                computerTrayInteger));
                pst.setInt(3, savedGame.getTileInHand());
                pst.setInt(4, (savedGame.isPlayersTurn() ? 1 : 0));
                pst.setInt(5, savedGame.getPlayerScore());
                pst.setInt(6, savedGame.getComputerScore());
                ((OraclePreparedStatement) pst).setARRAY(7,
                        ((OracleConnection) connection).createARRAY("T_WORDS",
                                savedGame.getLastWords()));
                pst.setInt(8, savedGame.getLastWordsPoints());
                ((OraclePreparedStatement) pst).setARRAY(9,
                        ((OracleConnection) connection).createARRAY("T_BAG",
                                bagRemainingInteger));
                System.out.println("meg elek!");                
                pst.executeUpdate();
                System.out.println("mar nem");                
            } else if (rset.getInt(1) == 0) {
                PreparedStatement pst = connection
                        .prepareStatement("insert into saved_games values("
                                + "null, '" + username + "',"
                                + "?, ?, ?, ?, ?, ?, ?, ?, ?)");

                ((OraclePreparedStatement) pst).setARRAY(1,
                        ((OracleConnection) connection).createARRAY("T_TRAY",
                                playerTrayInteger));
                ((OraclePreparedStatement) pst).setARRAY(2,
                        ((OracleConnection) connection).createARRAY("T_TRAY",
                                computerTrayInteger));
                pst.setInt(3, savedGame.getTileInHand());
                pst.setInt(4, (savedGame.isPlayersTurn() ? 1 : 0));
                pst.setInt(5, savedGame.getPlayerScore());
                pst.setInt(6, savedGame.getComputerScore());
                ((OraclePreparedStatement) pst).setARRAY(7,
                        ((OracleConnection) connection).createARRAY("T_WORDS",
                                savedGame.getLastWords()));
                pst.setInt(8, savedGame.getLastWordsPoints());
                ((OraclePreparedStatement) pst).setARRAY(9,
                        ((OracleConnection) connection).createARRAY("T_BAG",
                                bagRemainingInteger));

                pst.executeUpdate();
            }
            
            st.executeUpdate(String.format(
                    "update saved_games set last_words=t_words(%s) where username='%s'", 
                    Utils.stringArrayToString(savedGame.getLastWords()), username));
            
            System.out.println("id select");
            rset = st.executeQuery("select saved_game_id from saved_games where username='" + username + "'");
            if (rset.next()) {
                int id = rset.getInt(1);
                System.out.println("delete");
                st.executeUpdate("delete from fields where saved_game_id=" + id);
                System.out.println("insert");
                insertFields(id, savedGame.getFields());
            }
        } catch (SQLException e) {
            // TODO log - ez baj, ilyen nem kene legyen
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
