package Entity;

/**
 * 培训签到类
 */
public class Registrate {
    private int participantId;          //培训参与者编号
    private String participantName;     //培训参与者姓名
    private int trainingId;             //培训课程编号
    private int registrateFlag;         //培训签到，	1：已签到；0：未签到

    public int getParticipantId() {
        return participantId;
    }

    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public int getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(int trainingId) {
        this.trainingId = trainingId;
    }

	public int getRegistrateFlag() {
		return registrateFlag;
	}

	public void setRegistrateFlag(int registrateFlag) {
		this.registrateFlag = registrateFlag;
	}

}
