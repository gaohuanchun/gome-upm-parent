package com.gome.upm.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ExcelUtil {

	private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);
	
	
	
	public static void exportExcel(HttpServletResponse out, JSONArray datas, String[] title, String[] header, String fileName) {
		
		if (datas == null || header == null) {
			throw new IllegalArgumentException("写excel流需要参数!");
		}
		
		
		try {
			
			out.setContentType("application/vnd.ms-excel;charset=UTF-8");  
            out.setHeader("Content-disposition", "attachment;filename=\""+ new String((fileName).getBytes("GBK"),"ISO-8859-1") + "\"");    //这里的是要导出的文件显示的名字
            
			WritableWorkbook workbook = Workbook.createWorkbook(out.getOutputStream());
			WritableSheet ws = workbook.createSheet("sheet 1", 0);
			int rowNum = 0; // 要写的行
			if (title != null) {
				putRow(ws, 0, title);// 压入标题
				rowNum = 1;
			}

			for (int i = 0; i < datas.size(); i++, rowNum++) {// 写sheet
				JSONObject data = datas.getJSONObject(i);
				for (int k = 0; k < header.length; k++) {
					Label cell = new Label(k, rowNum, "" + data.get(header[k]));
					ws.addCell(cell);
				}

			}

			workbook.write();
			workbook.close(); // 一定要关闭, 否则没有保存Excel
		} catch (RowsExceededException e) {
			log.error("jxl write RowsExceededException: " + e.getMessage());
		} catch (WriteException e) {
			log.error("jxl write WriteException: " + e.getMessage());
		} catch (IOException e) {
			log.error("jxl write file i/o exception!, cause by: "
					+ e.getMessage());
		}
	}

	/**
	 * excel导出
	 * 
	 * @param out
	 * @param datas
	 * @param title
	 * @param header
	 */
	public static void exportExcel2(OutputStream out,
			List<Map<String, Object>> datas, String[] title, String[] header) {
		if (datas == null || header == null) {
			throw new IllegalArgumentException("写excel流需要参数!");
		}
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(out);
			WritableSheet ws = workbook.createSheet("sheet 1", 0);
			int rowNum = 0; // 要写的行
			if (title != null) {
				putRow(ws, 0, title);// 压入标题
				rowNum = 1;
			}

			for (int i = 0; i < datas.size(); i++, rowNum++) {// 写sheet
				Map<String, Object> data = datas.get(i);
				for (int k = 0; k < header.length; k++) {
					Label cell = new Label(k, rowNum, "" + data.get(header[k]));
					ws.addCell(cell);
				}

			}

			workbook.write();
			workbook.close(); // 一定要关闭, 否则没有保存Excel
		} catch (RowsExceededException e) {
			log.error("jxl write RowsExceededException: " + e.getMessage());
		} catch (WriteException e) {
			log.error("jxl write WriteException: " + e.getMessage());
		} catch (IOException e) {
			log.error("jxl write file i/o exception!, cause by: "
					+ e.getMessage());
		}
	}

	/**
	 * @param datas
	 *            封装着Object[]的列表, 一般是String内容.
	 * @param title
	 *            每个sheet里的标题.
	 */
	public static void writeExcel(OutputStream out, List datas, String[] title) {
		if (datas == null) {
			throw new IllegalArgumentException("写excel流需要List参数!");
		}
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(out);
			WritableSheet ws = workbook.createSheet("sheet 1", 0);
			int rowNum = 0; // 要写的行
			if (title != null) {
				putRow(ws, 0, title);// 压入标题
				rowNum = 1;
			}
			for (int i = 0; i < datas.size(); i++, rowNum++) {// 写sheet
				Object[] cells = (Object[]) datas.get(i);
				putRow(ws, rowNum, cells); // 压一行到sheet
			}

			workbook.write();
			workbook.close(); // 一定要关闭, 否则没有保存Excel
		} catch (RowsExceededException e) {
			log.error("jxl write RowsExceededException: " + e.getMessage());
		} catch (WriteException e) {
			log.error("jxl write WriteException: " + e.getMessage());
		} catch (IOException e) {
			log.error("jxl write file i/o exception!, cause by: "
					+ e.getMessage());
		}
	}

	/**
	 * 获取excel属性
	 * 
	 * @param inS
	 * @return
	 */
	public static String[] getExcelAttributes(InputStream inS) {
		try {
			Workbook workbook = Workbook.getWorkbook(inS);
			Sheet sheet = workbook.getSheet(0);
			int rows = getRows(sheet);
			int columns = getColumns(sheet);
			String[] attributesAll = getAttribute(sheet, 0, columns, 0);
			return attributesAll;
		} catch (BiffException e) {
			log.error("jxl BiffException: " + e.getMessage());
			// e.printStackTrace();
		} catch (IOException e) {
			log.error("jxl IOException: " + e.getMessage());
			// e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取ins中指定属性的值
	 * 
	 * @param ins
	 *            excel的输入流
	 * @param attributes
	 *            需要获取的属性(如果为空就获取全部属性)
	 */
	public static String[][] getExcelContents(InputStream inS,
			String[] attributes) {
		String[][] contents = null;
		try {
			Workbook workbook = Workbook.getWorkbook(inS);
			Sheet sheet = workbook.getSheet(0);
			int rows = getRows(sheet);
			int columns = getColumns(sheet);
			String[] attributesAll = getAttribute(sheet, 0, columns, 0);
			if (attributes == null) {
				attributes = attributesAll;
			}
			String[][] cellsArray = getCells(sheet, 1, rows, 0, columns);
			contents = getContentsFromFixAttri(attributes, attributesAll,
					cellsArray);

		} catch (BiffException e) {
			log.error("jxl BiffException: " + e.getMessage());
			// e.printStackTrace();
		} catch (IOException e) {
			log.error("jxl IOException: " + e.getMessage());
			// e.printStackTrace();
		}
		return contents;
	}

	/**
	 * 返回list list
	 * 
	 * @param filePath
	 * @param sheetNum
	 * @return
	 */
	public static List<List<String>> readListExcelFile(String filePath,
			int sheetNum) {
		List<List<String>> ls = new ArrayList<List<String>>();
		Workbook book = null;
		try {
			// 读Excel文件
			book = Workbook.getWorkbook(new File(filePath));
			// 获得工作表个数
			Sheet sheet = book.getSheet(sheetNum);
			for (int i = 0; i < sheet.getRows(); i++) {
				// 获得行
				Cell[] row = sheet.getRow(i);
				// Map<String, String> rowMap = new HashMap<String, String>();
				List<String> rowMap = new ArrayList<String>();
				for (int j = 0; j < row.length; j++) {

					// 获得单元格内容
					String content = row[j].getContents().trim();
					if (!StringUtils.isEmpty(content)) {
						// 因为从0开始,所以要+1
						rowMap.add(content);
					}
				}
				if (!rowMap.isEmpty()) {
					ls.add(rowMap);
				}
			}
		} catch (BiffException e) {
			log.error("BiffException", e);
		} catch (IOException e) {
			log.error("IOException", e);
		} finally {
			if (book != null) {
				book.close();
			}
		}
		return ls;
	}

	public static List<Map<String, String>> readExcelFile(String filePath,
			int sheetNum) {
		List<Map<String, String>> ls = new ArrayList<Map<String, String>>();
		Workbook book = null;
		try {
			// 读Excel文件
			book = Workbook.getWorkbook(new File(filePath));
			// 获得工作表个数
			Sheet sheet = book.getSheet(sheetNum);
			for (int i = 0; i < sheet.getRows(); i++) {
				// 获得行
				Cell[] row = sheet.getRow(i);
				Map<String, String> rowMap = new HashMap<String, String>();
				for (int j = 0; j < row.length; j++) {
					// 获得单元格内容
					String content = row[j].getContents();
					if (StringUtils.isNotBlank(content)) {
						// 因为从0开始,所以要+1
						rowMap.put(String.valueOf(j + 1), content);
					}
				}
				if (!rowMap.isEmpty()) {
					ls.add(rowMap);
				}
			}
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (book != null) {
				book.close();
			}
		}
		return ls;
	}

	/**
	 * 获取contentsAll中指定属性的值
	 * 
	 * @param attributes
	 *            需要获取的属性
	 * @param attributeAll
	 *            所有属性
	 * @param contentsAll
	 *            所有值
	 */
	public static String[][] getContentsFromFixAttri(String[] attributes,
			String[] attributeAll, String[][] contentsAll) {
		String[][] contents = null;
		List<Integer> indexS = new ArrayList<Integer>();
		if (attributes != null && attributeAll != null
				& attributeAll.length >= attributes.length) {
			for (int i = 0; i < attributes.length; i++) {
				for (int j = 0; j < attributeAll.length; j++) {
					if (attributes[i].equals(attributeAll[j])) {
						indexS.add(j);
					}
				}
			}
		}
		int columnCount = indexS.size();
		int rowCount = contentsAll.length;
		contents = new String[rowCount][columnCount];
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				contents[i][j] = contentsAll[i][indexS.get(j)];
			}
		}
		return contents;
	}

	/**
	 * 获取属性数组
	 * 
	 * @param sheet
	 *            工作薄
	 * @param 属性所在row
	 *            行数
	 * @param startColumn
	 *            开始列
	 * @param endColumn
	 *            结束列
	 */
	public static String[] getAttribute(Sheet sheet, int startColumn,
			int endColumn, int row) {
		String[] attributes = new String[endColumn - startColumn];
		int maxColumn = getColumns(sheet);
		int maxRow = getRows(sheet);
		if (row < maxRow) {
			for (int i = startColumn; i < endColumn && i < maxColumn; i++) {
				Cell attribute = sheet.getCell(i, row);
				attributes[i] = attribute.getContents();
			}
		}

		return attributes;
	}

	/**
	 * 获取每行单元格数组
	 * 
	 * @param sheet
	 *            工作薄
	 * @param startrow
	 *            行数
	 * @param endrow
	 *            结束行
	 * @param startcol
	 *            开始列
	 * @param endCol
	 *            结束列
	 * @return 每行单元格数组
	 */
	public static String[][] getCells(Sheet sheet, int startrow, int endrow,
			int startcol, int endcol) {
		String[][] cellArray = new String[endrow - startrow][endcol - startcol];
		int maxRow = getRows(sheet);
		int maxCos = getColumns(sheet);
		for (int i = startrow; i < endrow && i < maxRow; i++) {

			for (int j = startcol; j < endcol && j < maxCos; j++) {

				cellArray[i - startrow][j - startcol] = sheet.getCell(j, i)
						.getContents();
			}

		}
		return cellArray;
	}

	/**
	 * 获取工作薄总行数
	 * 
	 * @param sheet
	 *            工作薄
	 * @return 工作薄总行数
	 */
	public static int getRows(Sheet sheet) {
		return sheet == null ? 0 : sheet.getRows();
	}

	/**
	 * 获取最大列数
	 * 
	 * @param sheet
	 *            工作薄
	 * @return 总行数最大列数
	 */
	public static int getColumns(Sheet sheet) {
		return sheet == null ? 0 : sheet.getColumns();
	}

	/**
	 * 获取每行单元格数组
	 * 
	 * @param sheet
	 *            工作薄
	 * @param row
	 *            行数
	 * @return 每行单元格数组
	 */
	public static Cell[] getRows(Sheet sheet, int row) {
		return sheet == null || sheet.getRows() < row ? null : sheet
				.getRow(row);
	}

	/**
	 * 写一行
	 * 
	 * @param ws
	 * @param rowNum
	 * @param cells
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	private static void putRow(WritableSheet ws, int rowNum, Object[] cells)
			throws RowsExceededException, WriteException {
		for (int j = 0; j < cells.length; j++) {// 写一行
			Label cell = new Label(j, rowNum, "" + cells[j]);
			ws.addCell(cell);
		}
	}

}
