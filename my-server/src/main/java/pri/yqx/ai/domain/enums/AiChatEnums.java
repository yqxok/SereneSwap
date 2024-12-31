package pri.yqx.ai.domain.enums;

public enum AiChatEnums {
    AI(0),USER(1);
    private final Integer type;

    AiChatEnums(Integer type){
        this.type = type;
    }
    public Integer getType(){
        return type;
    }
}
