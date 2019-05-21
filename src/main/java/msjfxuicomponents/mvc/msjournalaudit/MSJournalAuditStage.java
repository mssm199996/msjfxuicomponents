package msjfxuicomponents.mvc.msjournalaudit;

import msdatabaseutils.IOperationTypeDAO;
import msdatabaseutils.OperationType;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.SimpleStageType;

public class MSJournalAuditStage<O extends OperationType> extends SimpleStageType<MSJournalAuditController<O>> {

	public MSJournalAuditStage() {
		super("Journal Audit", "/msjfxuicomponents/mvc/msjournalaudit/MSJournalAudit.fxml");

		this.setMaximized(true);

		MSJFXUIComponentsHolder.MS_JOURNAL_AUDIT_STAGE = this;
	}

	public void setSearcher(IOperationTypeDAO<?> searcher) {
		this.getController().setSearcher(searcher);
	}
}
