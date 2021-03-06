/*
 * #%L
 * ImgLib2: a general-purpose, multidimensional image processing library.
 * %%
 * Copyright (C) 2009 - 2016 Tobias Pietzsch, Stephan Preibisch, Stephan Saalfeld,
 * John Bogovic, Albert Cardona, Barry DeZonia, Christian Dietz, Jan Funke,
 * Aivar Grislis, Jonathan Hale, Grant Harris, Stefan Helfrich, Mark Hiner,
 * Martin Horn, Steffen Jaensch, Lee Kamentsky, Larry Lindsey, Melissa Linkert,
 * Mark Longair, Brian Northan, Nick Perry, Curtis Rueden, Johannes Schindelin,
 * Jean-Yves Tinevez and Michael Zinsmaier.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package interactive;

import interactive.remote.catmaid.CATMAIDRandomAccessibleInterval;
import io.scif.img.ImgIOException;
import net.imglib2.RandomAccessible;
import net.imglib2.converter.TypeIdentity;
import net.imglib2.realtransform.AffineTransform3D;
import net.imglib2.type.numeric.ARGBType;
import net.imglib2.ui.overlay.LogoPainter;
import net.imglib2.ui.viewer.InteractiveViewer3D;
import net.imglib2.view.Views;

public class InteractiveCATMAIDRotationExample
{
	final static public void main( final String[] args ) throws ImgIOException
	{
//		final CATMAIDRandomAccessibleInterval map =
//				new CATMAIDRandomAccessibleInterval(
//					"file:/home/saalfeld/tmp/catmaid/export-test/fib/aligned/xy/",
//					1987,
//					1441,
//					460,
//					0,
//					256,
//					256 );
		final CATMAIDRandomAccessibleInterval map =
				new CATMAIDRandomAccessibleInterval(
						"http://catmaid.mpi-cbg.de/map/c-elegans/",
						6016,
						4464,
						803,
						0,
						256,
						256 );
//		final CATMAIDRandomAccessibleInterval map = new CATMAIDRandomAccessibleInterval( 6016, 4464, 803, "http://localhost/catmaid/" );

		final int w = 400, h = 300;

		final double yScale = 1.0, zScale = 12.0;
//		final double yScale = 1.0, zScale = 2.0;
		final AffineTransform3D initial = new AffineTransform3D();
		initial.set(
			1.0, 0.0, 0.0, ( w - map.dimension( 0 ) ) / 2.0,
			0.0, yScale, 0.0, ( h - map.dimension( 1 ) * yScale ) / 2.0,
			0.0, 0.0, zScale, -( map.dimension( 2 ) / 2.0 - 0.5 ) * zScale );

		final RandomAccessible< ARGBType > extended = Views.extendValue( map, new ARGBType( 0xff006600 ) );
		final InteractiveViewer3D< ARGBType > viewer = new InteractiveViewer3D< ARGBType >( w, h, extended, map, initial, new TypeIdentity< ARGBType >() );
		viewer.getDisplayCanvas().addOverlayRenderer( new LogoPainter() );
		viewer.requestRepaint();
	}

}
