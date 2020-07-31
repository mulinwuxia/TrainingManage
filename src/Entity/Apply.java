package Entity;

/**
 * 培训申请类
 */
public class Apply {
    private int participantId;          //培训参与者编号
    private String participantName;     //培训参与者姓名
    private int trainingId;             //培训课程编号

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

}
