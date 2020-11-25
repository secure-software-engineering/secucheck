package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.internal;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.net.URL;

import com.ibm.wala.cast.tree.CAstSourcePositionMap.Position;
import com.ibm.wala.classLoader.IMethod.SourcePosition;

public class ReportPosition implements Position, Serializable {

	private int firstLine,
				lastLine,
				firstCol,
				lastCol,
				firstOffset,
				lastOffset;
	
	private URL url;
	private Reader reader;
	
	@Override public int getFirstLine() { return firstLine; }
	@Override public int getLastLine() { return lastLine; } 
	@Override public int getFirstCol()  { return firstCol; } 
	@Override public int getLastCol()  { return lastCol; } 
	@Override public int getFirstOffset()  { return firstOffset; } 
	@Override public int getLastOffset() { return lastOffset; } 
	@Override public URL getURL()  { return url; } 
	
	@Override public Reader getReader() throws IOException  { return reader; } 
	
	@Override public int compareTo(SourcePosition o) { 
		if (getFirstLine() != o.getFirstLine()) {
			return getFirstLine() > o.getFirstLine() ? 1 : -1;
		} else if (getLastLine() != o.getLastLine()) {
			return getLastLine() > o.getLastLine() ? 1 : -1;
		} else if (getFirstCol() != o.getFirstCol()) {
			return getFirstCol() > o.getFirstCol() ? 1 : -1;
		} else if (getLastCol() != o.getLastCol()) {
			return getLastCol() > o.getLastCol() ? 1 : -1;
		} else if (getFirstOffset() != o.getFirstOffset()) {
			return getFirstOffset() > o.getFirstOffset() ? 1 : -1;
		} else if (getLastOffset() != o.getLastOffset()) {
			return getLastOffset() > o.getLastOffset() ? 1 : -1;
		} else { return 0; } 
	} 
	
	public void setFirstLine(int firstLine) { this.firstLine = firstLine;}
	public void setLastLine(int lastLine) { this.lastLine = lastLine; }
	public void setFirstCol(int firstCol) { this.firstCol = firstCol; }
	public void setLastCol(int lastCol) { this.lastCol = lastCol; }
	public void setFirstOffset(int firstOffset) { this.firstOffset = firstOffset; }
	public void setLastOffset(int lastOffset) { this.lastOffset = lastOffset; }
	public void setUrl(URL url) { this.url = url; }
	public void setReader(Reader reader) { this.reader = reader; }
}
