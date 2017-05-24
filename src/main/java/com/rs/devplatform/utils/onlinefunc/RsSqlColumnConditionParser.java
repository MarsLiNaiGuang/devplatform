package com.rs.devplatform.utils.onlinefunc;

import java.util.ArrayList;
import java.util.List;

import com.rs.devplatform.vo.onlinefc.OnlineFcColumn;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.FromItemVisitorAdapter;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitorAdapter;
import net.sf.jsqlparser.statement.select.SelectVisitorAdapter;
import net.sf.jsqlparser.statement.select.SubSelect;

public class RsSqlColumnConditionParser {

	private List<SelectItem> columns = new ArrayList<>();
	private List<Expression> conditions = new ArrayList<>();
	
	public List<SelectItem> getColumns() {
		return columns;
	}

	public List<Expression> getConditions() {
		return conditions;
	}

	/**
	 * SQL:select username as name, age<BR>
	 * return: name, age
	 * @return
	 */
	public List<OnlineFcColumn> getColumnNames(){
		ColumnNameVisitor colNameVisitor = new ColumnNameVisitor();
		for(SelectItem item:columns){
			item.accept(colNameVisitor);
		}
		return colNameVisitor.getColumnNames();
	}

	public void parseSelect(Select selstmt) {
		CheckPlainSelect checkPlainSelect = new CheckPlainSelect();
		selstmt.getSelectBody().accept(checkPlainSelect);
		if (checkPlainSelect.isCheckPlainSelect()) {
			PlainSelect pselect = (PlainSelect) selstmt.getSelectBody();
			columns.addAll(pselect.getSelectItems());
			SubSelectVisit subSelectVisit = new SubSelectVisit();
			FromItem fromItem = pselect.getFromItem();
			fromItem.accept(subSelectVisit);
			if (subSelectVisit.isSubSelectSql()) {
				parseSubSelect((SubSelect) fromItem);
			} /*else {
				System.out.println("fromItem=" + fromItem);
			}*/
			Expression where = pselect.getWhere();
//			pselect.getOrderByElements();
			if (where != null) {
				SQLConditionExpressionVisitorAdapter adp = new SQLConditionExpressionVisitorAdapter();
				where.accept(adp);
				if(adp.hasExpression()){
					conditions.addAll(adp.getExpressions());
				}else{
					conditions.add(where);
				}
			}
		}
	}

	public void parseSubSelect(SubSelect subSelect) {
		CheckPlainSelect checkPlainSelect = new CheckPlainSelect();
		subSelect.getSelectBody().accept(checkPlainSelect);
		if (checkPlainSelect.isCheckPlainSelect()) {
			PlainSelect pselect = (PlainSelect) subSelect.getSelectBody();
			columns.addAll(pselect.getSelectItems());
			SubSelectVisit subSelectVisit = new SubSelectVisit();
			FromItem fromItem = pselect.getFromItem();
			fromItem.accept(subSelectVisit);
			if (subSelectVisit.isSubSelectSql()) {
				parseSubSelect((SubSelect) fromItem);
			} /*else {
				System.out.println("fromItem=" + fromItem);
			}*/
			Expression where = pselect.getWhere();
			if (where != null) {
				SQLConditionExpressionVisitorAdapter adp = new SQLConditionExpressionVisitorAdapter();
				where.accept(adp);
				if(adp.hasExpression()){
					conditions.addAll(adp.getExpressions());
				}else{
					conditions.add(where);
				}
			}
		}
	}
	
	
	public static class SQLConditionExpressionVisitorAdapter extends ExpressionVisitorAdapter {
		private List<Expression> expressions = new ArrayList<>();

		@Override
		public void visit(AndExpression expr) {
			if (expr.getLeftExpression() instanceof OrExpression || expr.getLeftExpression() instanceof AndExpression) {
				expr.getLeftExpression().accept(this);
			} else {
				expressions.add(expr.getLeftExpression());
			}
			if (expr.getRightExpression() instanceof OrExpression
					|| expr.getRightExpression() instanceof AndExpression) {
				expr.getRightExpression().accept(this);
			} else {
				expressions.add(expr.getRightExpression());
			}
		}

		@Override
		public void visit(OrExpression expr) {
			if (expr.getLeftExpression() instanceof OrExpression || expr.getLeftExpression() instanceof AndExpression) {
				expr.getLeftExpression().accept(this);
			} else {
				expressions.add(expr.getLeftExpression());
			}
			if (expr.getRightExpression() instanceof OrExpression
					|| expr.getRightExpression() instanceof AndExpression) {
				expr.getRightExpression().accept(this);
			} else {
				expressions.add(expr.getRightExpression());
			}
		}
		public List<Expression> getExpressions() {
			return expressions;
		}
		public boolean hasExpression(){
			return !expressions.isEmpty();
		}
	}
	
	public static class CheckPlainSelect extends SelectVisitorAdapter {

		private boolean checkPlainSelect = false;
		@Override
		public void visit(PlainSelect plainSelect) {
			checkPlainSelect = true;
		}
		public boolean isCheckPlainSelect() {
			return checkPlainSelect;
		}
	}
	
	public static class SubSelectVisit extends FromItemVisitorAdapter {

		private boolean subSelectSql;
		@Override
		public void visit(SubSelect subSelect) {
			subSelectSql = true;
		}
		public boolean isSubSelectSql() {
			return subSelectSql;
		}
	}
	
	public static class ColumnNameVisitor extends SelectItemVisitorAdapter{
		public static final String COLUMN_NAME = "column";
		public static final String ALIAS_NAME = "alias";
		
		private List<OnlineFcColumn> columnNames = new ArrayList<>();
		@Override
		public void visit(SelectExpressionItem item) {
			OnlineFcColumn column = new OnlineFcColumn();
			column.setColName(item.getExpression().toString());
			column.setAlias(item.getAlias()!=null?item.getAlias().getName():"");
			columnNames.add(column);
		}
		public List<OnlineFcColumn> getColumnNames() {
			return columnNames;
		}
	}
}
