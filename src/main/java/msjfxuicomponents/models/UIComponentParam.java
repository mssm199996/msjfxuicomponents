package msjfxuicomponents.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class UIComponentParam {

	private int id;
	private String stageId;
	private String uiComponentParamEnumId;
	private String value;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStageId() {
		return stageId;
	}

	public void setStageId(String stageId) {
		this.stageId = stageId;
	}

	public String getUiComponentParamEnumId() {
		return uiComponentParamEnumId;
	}

	public void setUiComponentParamEnumId(String uiComponentParamEnumId) {
		this.uiComponentParamEnumId = uiComponentParamEnumId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.getStageId() + " - " + this.getUiComponentParamEnumId() + " - " + this.getValue();
	}
}
