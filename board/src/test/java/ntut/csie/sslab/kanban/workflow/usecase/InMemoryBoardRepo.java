package ntut.csie.sslab.kanban.workflow.usecase;

import ntut.csie.sslab.kanban.board.entity.Board;
import ntut.csie.sslab.kanban.board.usecase.port.out.BoardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryBoardRepo implements BoardRepository {
    private List<Board> list = new ArrayList<>();

    @Override
    public Optional<Board> findById(String s) {
        return list.stream().filter(board -> board.getBoardId().equals(s)).findAny();
    }

    @Override
    public void save(Board data) {
        list.add(data);
    }

    @Override
    public void deleteById(String s) {

    }
}