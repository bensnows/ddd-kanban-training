package tw.teddysoft.clean.usecase.kanbanboard.old_stage.delete;

import tw.teddysoft.clean.domain.usecase.Input;

public interface DeleteStageInput extends Input {

    String getStageId();
    void setStageId(String id);

}