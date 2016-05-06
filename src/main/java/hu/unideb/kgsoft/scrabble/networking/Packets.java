package hu.unideb.kgsoft.scrabble.networking;

public class Packets {
	
	public static class RegisterPlayer {
		private String name;

		public RegisterPlayer(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}	
	}
	
	public static class ErrorMessage {
		private String message;

		public ErrorMessage(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}
}
