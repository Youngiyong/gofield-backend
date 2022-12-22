package com.gofield.common.excel.poi;

import com.gofield.common.excel.XSSFExcelFile;
import com.gofield.common.excel.poi.resource.DataFormatDecider;
import com.gofield.common.excel.poi.resource.DefaultDataFormatDecider;
import com.gofield.common.excel.poi.resource.ExcelCustomHeader;
import com.gofield.common.excel.poi.resource.ExcelRenderPoiResourceFactory;
import com.gofield.common.exception.ExcelException;

import java.util.List;

/**
 * PoiSheetExcelFile - poi XSSF 엑셀 차트 생성을 위한 ExcelFile class
 */
public final class PoiSheetExcelFile<T> extends XSSFExcelFile<T> {

	private static final int ROW_START_INDEX = 0;
	private static final int COLUMN_START_INDEX = 0;
	private int currentRowIndex = ROW_START_INDEX;

	public PoiSheetExcelFile(Class<T> type) {
		super(type);
	}

	public PoiSheetExcelFile(List<T> data, Class<T> type) {
		super(data, type);
	}

	public PoiSheetExcelFile(List<T> data, List<String> headerKeys, ExcelCustomHeader excelCustom) {
		super(data, headerKeys,  excelCustom);
	}

	public PoiSheetExcelFile(List<T> data, Class<T> type, DataFormatDecider dataFormatDecider) {
		super(data, type, dataFormatDecider);
	}

	@Override
	protected void validateData(List<T> data) {
		if (null == data) {
			throw new ExcelException("poi excel data is null", new NullPointerException());
		}
	}

	@Override
	public void renderExcel(List<T> data) {
		// 1. Create sheet and renderHeader
		sheet = wb.createSheet();
		sheet.setDefaultColumnWidth((short) 25);

		renderHeadersWithNewSheet(sheet, currentRowIndex++, COLUMN_START_INDEX);

		if (data.isEmpty()) {
			return;
		}

		// 2. Render Body
		for (Object renderedData : data) {
			renderBody(renderedData, currentRowIndex++, COLUMN_START_INDEX);
		}
	}

	@Override
	public void addRows(List<T> data) {
		renderBody(data, currentRowIndex++, COLUMN_START_INDEX);
	}

	@Override
	public void addSheet(List<T> data, Class<T> type) {
		currentRowIndex = 0;
		validateData(data);
		super.resource = ExcelRenderPoiResourceFactory.prepareRenderResource(type, wb, new DefaultDataFormatDecider());
		renderExcel(data);
	}

	@Override
	public void addSheet(List<T> data, List<String> header, ExcelCustomHeader customHeader) {
		currentRowIndex = 0;
		validateData(data);
		super.resource = ExcelRenderPoiResourceFactory.prepareRenderResource(header, data, wb, new DefaultDataFormatDecider(), customHeader);
		renderExcel(data);
	}




}
