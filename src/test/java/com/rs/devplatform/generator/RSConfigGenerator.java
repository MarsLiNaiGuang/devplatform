package com.rs.devplatform.generator;

import com.baomidou.mybatisplus.generator.ConfigGenerator;

public class RSConfigGenerator extends ConfigGenerator {
	
	@Deprecated
	private boolean column2PropertyIncludeTableSuffix = false;

	public boolean isColumn2PropertyIncludeTableSuffix() {
		return column2PropertyIncludeTableSuffix;
	}

	public void setColumn2PropertyIncludeTableSuffix(boolean column2PropertyIncludeTableSuffix) {
		this.column2PropertyIncludeTableSuffix = column2PropertyIncludeTableSuffix;
	}

}
