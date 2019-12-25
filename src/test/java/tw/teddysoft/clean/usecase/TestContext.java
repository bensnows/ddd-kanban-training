package tw.teddysoft.clean.usecase;

import tw.teddysoft.clean.adapter.gateway.kanbanboard.*;
import tw.teddysoft.clean.adapter.presenter.card.SingleCardPresenter;
import tw.teddysoft.clean.adapter.presenter.kanbanboard.lane.SingleStagePresenter;
import tw.teddysoft.clean.adapter.presenter.kanbanboard.workflow.SingleWorkflowPresenter;
import tw.teddysoft.clean.domain.model.DomainEventBus;
import tw.teddysoft.clean.domain.model.kanbanboard.workflow.Workflow;
import tw.teddysoft.clean.domain.model.kanbanboard.workspace.Workspace;
import tw.teddysoft.clean.domain.usecase.UseCase;
import tw.teddysoft.clean.usecase.card.CardRepository;
import tw.teddysoft.clean.usecase.card.create.CreateCardInput;
import tw.teddysoft.clean.usecase.card.create.CreateCardOutput;
import tw.teddysoft.clean.usecase.card.create.CreateCardUseCase;
import tw.teddysoft.clean.usecase.domainevent.DomainEventRepository;
import tw.teddysoft.clean.usecase.domainevent.FlowEventRepository;
import tw.teddysoft.clean.usecase.domainevent.handler.*;
import tw.teddysoft.clean.usecase.kanbanboard.board.BoardRepository;
import tw.teddysoft.clean.usecase.kanbanboard.board.CreateBoardUseCaseWithEventHandlerTest;
import tw.teddysoft.clean.usecase.kanbanboard.board.create.CreateBoardOutput;
import tw.teddysoft.clean.usecase.kanbanboard.home.HomeRepository;
import tw.teddysoft.clean.usecase.kanbanboard.lane.stage.create.CreateStageInput;
import tw.teddysoft.clean.usecase.kanbanboard.lane.stage.create.CreateStageOutput;
import tw.teddysoft.clean.usecase.kanbanboard.lane.stage.create.CreateStageUseCase;
import tw.teddysoft.clean.usecase.kanbanboard.lane.swimlane.create.CreateSwimlaneInput;
import tw.teddysoft.clean.usecase.kanbanboard.lane.swimlane.create.CreateSwimlaneOutput;
import tw.teddysoft.clean.usecase.kanbanboard.lane.swimlane.create.CreateSwimlaneUseCase;
import tw.teddysoft.clean.usecase.kanbanboard.workflow.WorkflowRepository;
import tw.teddysoft.clean.usecase.kanbanboard.workflow.create.CreateWorkflowInput;
import tw.teddysoft.clean.usecase.kanbanboard.workflow.create.CreateWorkflowOutput;
import tw.teddysoft.clean.usecase.kanbanboard.workflow.create.CreateWorkflowUseCase;
import tw.teddysoft.clean.usecase.kanbanboard.workspace.CreateWorkspaceUseCaseTest;
import tw.teddysoft.clean.usecase.kanbanboard.workspace.WorkspaceRepository;
import tw.teddysoft.clean.usecase.kanbanboard.workspace.create.CreateWorkspaceOutput;
import tw.teddysoft.clean.usecase.user.UserRepository;

public class TestContext {

    public static final String USER_LOGIN_ID = "teddy@teddysoft.tw";
    public static final String USER_PASSWORD = "!@#124ASdfadyd08kdvF@tddd";
    public static final String USER_NAME = "Teddy Chen";
    public static final String USER_EMAIL = "teddy@teddysoft.tw";

    public static final String USER_ID = "USER-ID-8967";
    public static final String WORKSPACE_ID = "WORKSPACE-ID-5678";
    public static final String WORKSPACE_NAME = "Teddy's Workspace";
    public static final String WORKFLOW_NAME = "DEFAULT";

    public static final String SCRUM_BOARD_NAME = "Scrum Board";
    public static final String KANBAN_BOARD_NAME = "Kanban Board";

    private UserRepository userRepository;
    private HomeRepository homeRepository;
    private WorkspaceRepository workspaceRepository;
    private WorkflowRepository workflowRepository;
    private BoardRepository boardRepository;
    private CardRepository cardRepository;
    private DomainEventRepository storedEventRepository;
    private FlowEventRepository flowEventRepository;


    private WorkflowEventHandler workflowEventHandler;
    private FlowEventHandler flowEventHandler;
    private EventSourcingHandler eventSourcingHandler;

    public String workspaceId;
    public String boardId;

    private String defaultWorkspaceId;

    public static final String KANBAN_STAGE_1_READY_NAME = "Ready";
    public static final String KANBAN_STAGE_NAME_2_ANALYSIS = "Analysis";

    private Workflow kanbanDefaultWorkflow;
    private Workflow scrumDefaultWorkflow;

    private DomainEventBus eventBus;

    public static final int TOTAL_WORKFLOW_NUMBER = 2;

    public TestContext(){
        this(  new UserRepository(new InMemoryAggregateRootRepositoryPeer()),
                new HomeRepository(new InMemoryAggregateRootRepositoryPeer()),
                new WorkspaceRepository(new InMemoryAggregateRootRepositoryPeer()),
                new BoardRepository(new InMemoryAggregateRootRepositoryPeer()),
                new WorkflowRepository(new InMemoryAggregateRootRepositoryPeer()),
                new CardRepository(new InMemoryAggregateRootRepositoryPeer()),
                new FlowEventRepository(new InMemoryDomainEventRepositoryPeer()),
                new DomainEventRepository(new InMemoryDomainEventRepositoryPeer()));
    }
    public static TestContext newInstance() {
        return new TestContext();
    }

    public TestContext(
            UserRepository userRepository,
            HomeRepository homeRepository,
            WorkspaceRepository workspaceRepository,
            BoardRepository boardRepository,
                       WorkflowRepository workflowRepository,
                       CardRepository cardRepository,
                        FlowEventRepository flowEventRepository,
                        DomainEventRepository storedEventRepository){

        this.userRepository = userRepository;
        this.homeRepository = homeRepository;
        this.workspaceRepository = workspaceRepository;
        this.boardRepository = boardRepository;
        this.workflowRepository = workflowRepository;
        this.cardRepository = cardRepository;
        this.flowEventRepository = flowEventRepository;
        this.storedEventRepository = storedEventRepository;

        this.eventBus = new DomainEventBus();

        this.workflowEventHandler = new WorkflowEventHandler(this.getBoardRepository(), this.getWorkflowRepository(), eventBus);
        this.flowEventHandler = new FlowEventHandler(flowEventRepository, eventBus);
        this.eventSourcingHandler = new EventSourcingHandler(storedEventRepository);

    }

    public void registerAllEventHandler(){
        eventBus.register(workflowEventHandler);
        eventBus.register(new BoardEventHandler(workspaceRepository, workflowRepository, eventBus));
        eventBus.register(new WorkspaceEventHandler(workspaceRepository, workflowRepository, eventBus));
        eventBus.register(flowEventHandler);
        eventBus.register(eventSourcingHandler);
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public HomeRepository getHomeRepository() {
        return homeRepository;
    }

    public DomainEventRepository getStoredEventRepository() {
        return storedEventRepository;
    }

    public FlowEventRepository getFlowEventRepository() {
        return flowEventRepository;
    }

    public FlowEventHandler getFlowEventHandler() {
        return flowEventHandler;
    }

    public WorkflowEventHandler getWorkflowEventHandler(){
        return workflowEventHandler;
    }

    public DomainEventBus getDomainEventBus(){
        return eventBus;
    }

    public static UseCase<CreateWorkflowInput, CreateWorkflowOutput>
                newCreateWorkflowUseCase(WorkflowRepository workflowRepository, DomainEventBus eventBus){

        return new CreateWorkflowUseCase(workflowRepository, eventBus);
    }

    public WorkspaceRepository getWorkspaceRepository() {
        return workspaceRepository;
    }
    public BoardRepository getBoardRepository(){
        return boardRepository;
    }
    public WorkflowRepository getWorkflowRepository(){
        return workflowRepository;
    }
    public CardRepository getCardRepository(){
        return cardRepository;
    }


    public CreateWorkspaceOutput doCreateWorkspaceUseCase(String userId, String workspaceName) {
        return CreateWorkspaceUseCaseTest.doCreateWorkspaceUseCase(workspaceRepository, userId, workspaceName);
    }

    public Workspace getDefaultWorkspace(){
        return workspaceRepository.findAll().get(0);
    }

    public CreateBoardOutput doCreateBoardUseCase(String workspaceId, String boardName) {
        return CreateBoardUseCaseWithEventHandlerTest.doCreateBoardUseCase(workspaceId,
                boardName, workspaceRepository, boardRepository, workflowRepository, eventBus);
    }

    public CreateWorkflowOutput doCreateWorkflowUseCase(String boardId, String name) {
        UseCase<CreateWorkflowInput, CreateWorkflowOutput> createWorkflowUC =
                new CreateWorkflowUseCase(workflowRepository, eventBus);

        CreateWorkflowInput input = createWorkflowUC.createInput();
        CreateWorkflowOutput output = new SingleWorkflowPresenter();
        input.setBoardId(boardId);
        input.setWorkflowName(name);

        createWorkflowUC.execute(input, output);
        return output;
    }

    public CreateStageOutput doCreateStageUseCase(String workflowId, String name, String parentId){

        UseCase<CreateStageInput, CreateStageOutput> createStageLaneUC =
                new CreateStageUseCase(workflowRepository, getDomainEventBus());

        CreateStageInput input = createStageLaneUC.createInput();
        CreateStageOutput output = new tw.teddysoft.clean.adapter.presenter.kanbanboard.lane.SingleStagePresenter();
        input.setWorkflowId(workflowId);
        input.setName(name);
        input.setParentId(parentId);

        createStageLaneUC.execute(input, output);

        return output;
    }

    public CreateSwimlaneOutput doCreateSwimlaneUseCase(String workflowId, String LaneName, String parentId){

        UseCase<CreateSwimlaneInput, CreateSwimlaneOutput> createSwimLaneUC =
                new CreateSwimlaneUseCase(workflowRepository, getDomainEventBus());

        CreateSwimlaneInput input = createSwimLaneUC.createInput();
        CreateSwimlaneOutput output = new SingleStagePresenter();

        input.setWorkflowId(workflowId);
        input.setParentId(parentId);
        input.setTitle(LaneName);

        createSwimLaneUC.execute(input, output);

        return output;
    }

    public CreateCardOutput doCreateCardUseCase(String name, String workflowId, String laneId) {

        UseCase<CreateCardInput, CreateCardOutput> createCardUseCase =
                new CreateCardUseCase(cardRepository, workflowRepository, getDomainEventBus());

        CreateCardInput input = createCardUseCase.createInput() ;
        CreateCardOutput output = new SingleCardPresenter();
        input.setName(name);
        input.setWorkflowId(workflowId)
                .setLaneId(laneId);

        createCardUseCase.execute(input, output);
        return output;
    }


//    public void createScrumBoardAndStage() {
//        scrumBoard = createBoard("Scrum Board");
//
//        CreateWorkflowInput input = CreateWorkflowUseCaseImpl.createInput();
//        CreateWorkflowOutput output = new SingleWorkflowPresenter();
//        input.setBoardId(scrumBoard.getId());
//        input.setWorkflowName("Default Workflow");
//
////        CreateWorkflowUseCase createWorkflowUC = new CreateWorkflowUseCaseImpl(boardRepository, workflowRepository);
////        createWorkflowUC.execute(input, output);
//
//        scrumDefaultWorkflow = workflowRepository.findById(output.getWorkflowId());
//        scrumBoard.commitWorkflow(output.getWorkflowId());
//
//        create_stage(output.getWorkflowId(), "To Do", null, workflowRepository);
//        create_stage(output.getWorkflowId(), "Doing", null, workflowRepository);
//        create_stage(output.getWorkflowId(), "Done", null, workflowRepository);
//    }
//
//
//    public void createCardOnScrumBoard(String [] cardTitles) throws WipLimitExceedException {
//        System.out.println("==> " + scrumDefaultWorkflow.getStages().get(0).getId());
//        createCard(cardTitles, scrumDefaultWorkflow, scrumDefaultWorkflow.getStages().get(0).getId());
//    }
//
//
//    public void createKanbanBoardAndStage() {
//        kanbanBoard = createBoard("Kanban Board");
//
//        CreateWorkflowInput input = CreateWorkflowUseCaseImpl.createInput();
//        CreateWorkflowOutput output = new SingleWorkflowPresenter();
//        input.setBoardId(kanbanBoard.getId());
//        input.setWorkflowName("Default Workflow");
//        CreateWorkflowUseCase createWorkflowUC = new CreateWorkflowUseCaseImpl(boardRepository, workflowRepository);
//        createWorkflowUC.execute(input, output);
//        kanbanDefaultWorkflow = workflowRepository.findById(output.getWorkflowId());
//        kanbanBoard.commitWorkflow(output.getWorkflowId());
//
//        create_stage(output.getWorkflowId(), KANBAN_STAGE_1_READY_NAME, null, workflowRepository);
//        create_stage(output.getWorkflowId(), "Analysis", null, workflowRepository);
//        create_stage(output.getWorkflowId(), "Development", null, workflowRepository);
//        create_stage(output.getWorkflowId(), "Test", null, workflowRepository);
//        create_stage(output.getWorkflowId(), "Ready to Deploy", null, workflowRepository);
//        create_stage(output.getWorkflowId(), "Deployed", null, workflowRepository);
//
////        ready = stageRepository.findFirstByName("Ready");
////        analysis = stageRepository.findFirstByName("Analysis");
////        development = stageRepository.findFirstByName("Development");
////        test = stageRepository.findFirstByName("Test");
////        readyToDeploy = stageRepository.findFirstByName("Ready to Deploy");
////        deployed = stageRepository.findFirstByName("Deployed");
//    }
//
//    public Board createBoard(String name) {
//        Board board = new Board(name, WORKSPACE_ID);
//        boardRepository.save(board);
//        return board;
//    }
//
//    public void createCardOnKanbanBoard(String[] cardTitles) throws WipLimitExceedException {
//        createCard(cardTitles, kanbanDefaultWorkflow, kanbanDefaultWorkflow.getStages().get(0).getId());
//    }
//
//    private void createCard(String[] cardTitles, Workflow workflow, String laneId) throws WipLimitExceedException {
//        for (String each : cardTitles) {
//            createCard(workflow.getId(), laneId, each, this.getCardRepository(), this.getWorkflowRepository());
//        }
//    }
//
//    public Workflow getKanbanDefaultWorkflow() {
//        return kanbanDefaultWorkflow;
//    }
//
//    public Workflow getScrumDefaultWorkflow() {
//        return scrumDefaultWorkflow;
//    }
//
//    public static String create_workflow(String boardId, String title, BoardRepository boardRepository, WorkflowRepository repository) {
//        CreateWorkflowUseCase createWorkflowUC = new CreateWorkflowUseCaseImpl(boardRepository, repository);
//
//        CreateWorkflowInput input = CreateWorkflowUseCaseImpl.createInput();
//        CreateWorkflowOutput output = new SingleWorkflowPresenter();
//        input.setBoardId(boardId);
//        input.setWorkflowName(title);
//
//        createWorkflowUC.execute(input, output);
//        return output.getWorkflowId();
//    }
//
//
//    public static String create_stage(String workflowId, String title, String parentId, WorkflowRepository repository){
//
//        CreateStageUseCase createStageLaneUC = new CreateStageUseCaseImpl(repository);
//        CreateStageInput input = CreateStageUseCaseImpl.createInput();
//        CreateStageOutput output = new SingleStagePresenter();
//        input.setWorkflowId(workflowId);
//        input.setTitle(title);
//        input.setParentId(parentId);
//
//        createStageLaneUC.execute(input, output);
//
//        return output.getId();
//    }
//
//
//    public static String create_swimlane(String workflowId, String LaneName, String parentId, WorkflowRepository repository){
//
//        CreateSwimlaneUseCase createSwimLaneUC = new CreateSwimlaneUseCaseImpl(repository);
//
//        CreateSwimlaneInput input = CreateSwimlaneUseCaseImpl.createInput();
//        CreateSwimlaneOutput output = new SingleStagePresenter();
//
//        input.setWorkflowId(workflowId);
//        input.setParentId(parentId);
//        input.setTitle(LaneName);
//
//        createSwimLaneUC.execute(input, output);
//
//        return output.getId();
//    }
//
//
//
//    public static String createCard(String workflowId, String landId, String title, CardRepository carRepository, WorkflowRepository workflowRepository){
//        CreateCardOutput output = doCreateCardUseCase(workflowId, landId, title, carRepository, workflowRepository);
//        return output.getId();
//    }
//
//
//
//    public static CreateCardOutput doCreateCardUseCase(String workflowId, String landId, String title, CardRepository cardRepository, WorkflowRepository workflowRepository){
//
//        CreateCardUseCase createCardUseCase = new CreateCardUseCaseImpl(cardRepository, workflowRepository);
//        CreateCardInput input = CreateCardUseCaseImpl.createInput() ;
//        CreateCardOutput output = new SingleCardPresenter();
//
//        input.setName(title);
//        input.setWorkflowId(workflowId);
//        input.setLaneId(landId);
//
//        createCardUseCase.execute(input, output);
//
//        return output;
//    }
//
//    public String createCardInScrumbaordTodoStage(String title) {
//        Workflow workflow = getScrumDefaultWorkflow();
//        Lane todoStage = workflow.getStages().get(0);
//
//        CreateCardUseCase createCardUseCase = new CreateCardUseCaseImpl(getCardRepository(), getWorkflowRepository());
//        CreateCardInput input = CreateCardUseCaseImpl.createInput() ;
//        CreateCardOutput output = new SingleCardPresenter();
//        input.setName(title);
//        input.setWorkflowId(workflow.getId())
//                .setLaneId(todoStage.getId());
//
//        createCardUseCase.execute(input, output);
//        return output.getId();
//    }
//
//    public Lane getReady() {
//        return kanbanDefaultWorkflow.getStages().get(0);
//    }
//
//    public Lane getAnalysis() {
//        return kanbanDefaultWorkflow.getStages().get(1);
//    }
//
//    public Lane getDevelopment() {
//        return kanbanDefaultWorkflow.getStages().get(2);
//    }
//
//    public Lane getTest() {
//        return kanbanDefaultWorkflow.getStages().get(3);
//    }
//
//    public Lane getReadyToDeploy() {
//        return kanbanDefaultWorkflow.getStages().get(4);
//    }
//
//    public Lane getDeployed() {
//        return kanbanDefaultWorkflow.getStages().get(5);
//    }



}

