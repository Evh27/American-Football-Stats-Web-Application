package uk.co.richardgoater.stats.tests.upload.mapping;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import uk.co.richardgoater.stats.persistence.ReceivingGameData;
import uk.co.richardgoater.stats.upload.excel.mapping.ReceivingRowMapper;


public class ReceivingRowMapperTest extends RowMapperTest { 
	
	private ReceivingRowMapper receivingRowMapper;
	private ReceivingGameData mappedRecevingGameData;
	
	@Override
	protected void setPlayerName() {playerName = "Craig Edmunds";}
	private int rec = 5;
	private int yds = 69;
	private String displayLong = "46T";
	private int longest = 46;
	private boolean isLongTD = true;
	private int td = 2;
	
	
	
	@Override
	protected void instantiateRowMapper() {
		receivingRowMapper = new ReceivingRowMapper();
		receivingRowMapper.setDao(mockDAO);
	}

	@Override
	protected void mapGameData() throws Exception {
		mappedRecevingGameData = (ReceivingGameData) receivingRowMapper.map(mockRow);
	}
	
	@Override
	protected void setRowExpectations() {
		setNextCellNumberOfTimesExpectation(5);
	}
	
	@Override
	protected void setCellExpectations() {
		expect(mockCell.asInt()).andReturn(rec);
		expect(mockCell.asInt()).andReturn(yds);
		expect(mockCell.asString()).andReturn(displayLong);
		expect(mockCell.asInt()).andReturn(td);
	}
	
	@Test
	public void mapsReceptions() {
		assertEquals(rec, mappedRecevingGameData.getRec());
	}	
	
	@Test
	public void mapsyards() {
		assertEquals(yds, mappedRecevingGameData.getYds());
	}
	
	@Test
	public void mapsLongest() {
		assertEquals(longest, mappedRecevingGameData.getLongest());
	}
	
	@Test
	public void mapsIsLongTD() {
		assertEquals(isLongTD, mappedRecevingGameData.isLongTD());
	}
	
	@Test
	public void mapsTd() {
		assertEquals(td, mappedRecevingGameData.getTd());
	}
	
	@Test
	public void mapsSeasonid() {
		assertEquals(seasonid, mappedRecevingGameData.getSeasonid());
	}
	
	@Test
	public void mapsWeeknum() {
		assertEquals(weeknum, mappedRecevingGameData.getWeeknum());
	}

}
