package uk.co.richardgoater.stats.upload.excel.mapping;

import uk.co.richardgoater.stats.persistence.GameData;
import uk.co.richardgoater.stats.persistence.dao.StatsDAO;
import uk.co.richardgoater.stats.upload.excel.ExcelRow;

public abstract class AbstractExcelRowMapper {

	protected StatsDAO dao;
	protected Object mappedObject;
	
	public void setDao(StatsDAO dao) {
		this.dao = dao;
	}
	
	public StatsDAO getDao() {
		return dao;
	}
	
	public Object map(ExcelRow row) throws Exception {
		mappedObject = getNewInstance();
		
		setScheduleData(row);		
		row.resetIterator();
		setPlayerId(row);
		mapColumns(row);
		
		return mappedObject;
	}
	
	protected void setScheduleData(ExcelRow row) {
		((GameData) mappedObject).setSeasonid(row.getSeasonid());
		((GameData) mappedObject).setWeeknum(row.getWeeknum());
	}
	
	protected void setPlayerId(ExcelRow row) throws Exception {
		((GameData) mappedObject).setPlayerid(dao.getPlayeridForName(row.nextCell().asString(), row.getSeasonid()));
	}

	public abstract void mapColumns(ExcelRow row);
	protected abstract Object getNewInstance();
	
}
