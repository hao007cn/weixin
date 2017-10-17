package com.senyint.core.entity.component;

import java.io.Serializable;

public interface Markable extends Serializable {

	public boolean isMarked();

	public void setMarked(boolean marked);
}
