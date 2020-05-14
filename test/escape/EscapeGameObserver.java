/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package escape;

/**
 * Description
 * @version May 14, 2020
 */
public class EscapeGameObserver implements GameObserver
{

	/*
	 * @see escape.GameObserver#notify(java.lang.String)
	 */
	@Override
	public void notify(String message)
	{
		System.out.println(message);
	}

	/*
	 * @see escape.GameObserver#notify(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void notify(String message, Throwable cause)
	{
		System.out.println(message);
	}

}