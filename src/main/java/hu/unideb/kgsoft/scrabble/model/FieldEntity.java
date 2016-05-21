package hu.unideb.kgsoft.scrabble.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import hu.unideb.kgsoft.scrabble.model.Field.Status;

@Entity
@Table(name = "wordgame_game_board_fields")
public class FieldEntity {	
	
	@Id
	@Column(name = "field_id")
	@SequenceGenerator(name = "IdGenerator", sequenceName = "wordgame_field_id_s", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdGenerator")
	private int fieldId;
	
	@ManyToOne
	@JoinColumn(name = "saved_game_id", nullable = false)
	private SavedGame savedGame;
	
	@Column(name = "row_index")
	private int rowIndex;
	
	@Column(name = "column_index")
	private int columnIndex;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;
	
	@Column(name = "tile_code")
	private int tileCode;
	
	@Column(name = "joker_tile_code")
	private int jokerTileCode;

	public FieldEntity() {
	}

	public FieldEntity(int fieldId, SavedGame savedGame, int rowIndex, int columnIndex, Status status, int tileCode,
			int jokerTileCode) {
		this.fieldId = fieldId;
		this.savedGame = savedGame;
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
		this.status = status;
		this.tileCode = tileCode;
		this.jokerTileCode = jokerTileCode;
	}

	public int getFieldId() {
		return fieldId;
	}

	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}

	public SavedGame getSavedGame() {
		return savedGame;
	}

	public void setSavedGame(SavedGame savedGame) {
		this.savedGame = savedGame;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getTileCode() {
		return tileCode;
	}

	public void setTileCode(int tileCode) {
		this.tileCode = tileCode;
	}

	public int getJokerTileCode() {
		return jokerTileCode;
	}

	public void setJokerTileCode(int jokerTileCode) {
		this.jokerTileCode = jokerTileCode;
	}	
	
}
