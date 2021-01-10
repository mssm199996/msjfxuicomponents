package msjfxuicomponents.daos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import msdatabaseutils.IDAO;
import msdatabaseutils.IOperationTypeDAO;
import msdatabaseutils.OperationType;
import msdatabaseutils.SessionFactoryHandler;
import msjfxuicomponents.models.UIComponentParam;

public class UIComponentParamDAO implements IDAO<UIComponentParam> {

	private SessionFactoryHandler sessionFactoryHandler;
	private IOperationTypeDAO<? extends OperationType> operationTypeDao;

	public UIComponentParamDAO(SessionFactoryHandler sessionFactoryHandler,
			IOperationTypeDAO<? extends OperationType> operationTypeDao) {
		this.sessionFactoryHandler = sessionFactoryHandler;
		this.operationTypeDao = operationTypeDao;
	}

	@SuppressWarnings("unchecked")
	public Map<String, UIComponentParam> findAllByStageId(String stageId) {
		List<UIComponentParam> result = (List<UIComponentParam>) (Object) this.sessionFactoryHandler
				.select("FROM UIComponentParam WHERE stageId = ?", stageId);

		if (result.isEmpty())
			return new HashMap<>();

		Map<String, UIComponentParam> resultAsMap = new HashMap<>();
		result.forEach(e -> resultAsMap.put(e.getUiComponentParamEnumId(), e));

		return resultAsMap;
	}

	@Override
	public String getGlobalSelectionQuery() {
		return "FROM UIComponentParam";
	}

	@Override
	public String onInsert(UIComponentParam entity) {
		return null;
	}

	@Override
	public String onDelete(UIComponentParam entity) {
		return null;
	}

	@Override
	public String onUpdate(UIComponentParam entity) {
		return null;
	}

	@Override
	public String getNameOfEntity() {
		return "UIComponentParam";
	}

	@Override
	public SessionFactoryHandler getSessionFactoryHandler() {
		return this.sessionFactoryHandler;
	}

	@Override
	public IOperationTypeDAO<? extends OperationType> getOperationDAO() {
		return this.operationTypeDao;
	}
}
