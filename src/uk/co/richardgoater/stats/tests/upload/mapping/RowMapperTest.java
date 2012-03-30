package uk.co.richardgoater.stats.tests.upload.mapping;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import org.junit.Before;
import org.junit.Test;

import uk.co.richardgoater.stats.persistence.dao.StatsDAOImpl;
import uk.co.richardgoater.stats.upload.excel.ExcelCell;
import uk.co.richardgoater.stats.upload.excel.ExcelRow;

public abstract class RowMapperTest {

	protected ExcelRow mockRow;
	protected StatsDAOImpl mockDAO;
	protected ExcelCell mockCell;
	
	protected String playerName;
	protected abstract void setPlayerName();
	protected int playerid = 50;
	protected int seasonid = 1;
	protected int weeknum = 1;

	protected void setUpMocks() {
		mockRow = createMock(ExcelRow.class);
		mockDAO = createMock(StatsDAOImpl.class);
		mockCell = createMock(ExcelCell.class);
	}

	protected void setExpectations() {
		expect(mockRow.getSeasonid()).andReturn(seasonid);
		expect(mockRow.getWeeknum()).andReturn(weeknum);
		mockRow.resetIterator();
		expectLastCall();
		setMapperRowExpectations();
		replay(mockRow);
		
		expect(mockCell.asString()).andReturn(playerName);
		setMapperCellExpectations();
		replay(mockCell);
		
		expect(mockDAO.getPlayeridForName(playerName)).andReturn(playerid);
		replay(mockDAO);
	}
	
	protected abstract void setMapperRowExpectations();
	protected void setNextCellNumberOfTimesExpectation(int numberOfTimes) {
		expect(mockRow.nextCell()).andReturn(mockCell).times(numberOfTimes);
	}
	
	protected abstract void setMapperCellExpectations();
	protected abstract void instantiateRowMapper();
	protected abstract void mapGameData();
	
	@Before
	public void setUp() {
		setUpMocks();
		setExpectations();
		instantiateRowMapper();
		mapGameData();
	}
	
	@Test
	public void verifyRow() {
		verify(mockRow);
	}
	
	@Test
	public void verifyCells() {
		verify(mockCell);
	}

}
