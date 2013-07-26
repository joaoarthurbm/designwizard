public class CallSecondLevel {
	public static void callSecondLevel() {
		CallerFirstLevel.callFirstLevel();
		Monitored.called();
	}
}
