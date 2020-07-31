package Entity;

/**
 * 培训成绩类
 */
public class Grade {
    private int participantId;          //培训参与者编号
    private String participantName;     //培训参与者姓名
    private int trainingId;             //培训课程编号
    private double score;               //培训成绩

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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
