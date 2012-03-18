package uk.co.richardgoater.stats.upload.excel.mapping;

import uk.co.richardgoater.stats.persistence.GameData;
import uk.co.richardgoater.stats.persistence.PassingGameData;
import uk.co.richardgoater.stats.upload.excel.ExcelRow;

public class PassingRowMapper 
extends ExcelRowMapperImpl 
implements ExcelRowMapper {
	
	@Override
	public GameData getNewGameDataInstance() {
		return new PassingGameData();
	}
	
	@Override
	public void mapStatsColumns(ExcelRow row) 
	{
		PassingGameData passingGameData = (PassingGameData) gameData;
		
		passingGameData.setAtt(row.nextCell().asInt());
		passingGameData.setComp(row.nextCell().asInt());
		passingGameData.setYds(row.nextCell().asInt());
		passingGameData.setLongStats(row.nextCell().asString());
		passingGameData.setTd(row.nextCell().asInt());
		passingGameData.setInts(row.nextCell().asInt());
		passingGameData.setSck(row.nextCell().asInt());
		passingGameData.setSackYds(row.nextCell().asInt());
	}

}
