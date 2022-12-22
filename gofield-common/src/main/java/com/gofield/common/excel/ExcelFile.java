package com.gofield.common.excel;

import com.gofield.common.excel.poi.resource.ExcelCustomHeader;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface ExcelFile<T>  {

	void write(OutputStream stream) throws IOException;

	void addRows(List<T> data);

	void addSheet(List<T> data, Class<T> type);

	void addSheet(List<T> data, List<String> header, ExcelCustomHeader customHeader);

}
