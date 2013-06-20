/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blinddog2.logic;

/**
 *
 * @author hady
 */
public class PositionConverter {
	private static com.blinddog2.logic.PositionConverter instance;

	private PositionConverter() {

	}

	public static com.blinddog2.logic.PositionConverter getInstance() {
		if (instance != null) {
			return instance;
		}
		return instance = new com.blinddog2.logic.PositionConverter();
	}

    //_______________________________________________________________
        

    public static final float UPPER_LEFT_POS_E= 455800;
    public static final float UPPER_LEFT_POS_N= 5724875;
    public static final float UPPER_RIGHT_POS_E = 455847;
    public static final float UPPER_RIGHT_POS_N = 5724879;
    public static final float DOWN_LEFT_POS_E = 455802;
    public static final float DOWN_LEFT_POS_N = 5724857;
    public static final float DOWN_RIGHT_POS_E = 455849;
    public static final float DOWN_RIGHT_POS_N = 5724863;
    public static final float ZERO_POS = 1f;
    


    
    public int DegreeInMeter(){
        return 1;
    }
}