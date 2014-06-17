package com.github.yaterz.ascii.spiral.main

import com.github.yaterz.ascii.spiral.exception.InvalidInputException

import static com.github.yaterz.ascii.spiral.main.Direction.*

/**
 * AsciiSpiral
 *
 * @author mattyaterz
 * @since 2014-06-17
 */
class AsciiSpiral {
    /**
     * Program entry point
     * @param args a single positive integer representing the size of the desired spiral
     */
    static void main( String[] args ) {
        int length = getMaxSpiralValue( args )
        def spiral = getMatrixFromMaxValue( length )
        buildSpiralInMatrix( spiral, length )
        println getAsciiFromMatrix( spiral )
    }

    /**
     * Argument validation
     * @param args the varargs from the {@link #main(java.lang.String[]) main} method
     * @return the length of the spiral to create
     * @throws InvalidInputException
     */
    static int getMaxSpiralValue( String[] args ) {
        if ( !args || args.length > 1 ) {
            throw new InvalidInputException( "Expecting a single argument, not $args" )
        }

        int spiralSize

        try {
            spiralSize = args[0] as int

            if ( spiralSize < 0 ) {
                throw new InvalidInputException( "Expecting a positive integer, not $spiralSize" )
            }
        }
        catch ( ex ) {
            throw new InvalidInputException( "Expecting an integer, not ${args[0]}", ex )
        }

        spiralSize
    }

    /**
     * Build the necessary matrix in which to hold the centered spiral and populate the square up to (inclusively) {@code maxValue}
     *
     * <p/>Example 1 - {@code maxValue = 0}
     * Special case, 1x1 square
     *
     * <p/>Example 2 - {@code maxValue = 1}
     * √1 = 1 + 1 = 2 rounded down = 2
     *  2 += ( 2 + 1 = 3 % 2 = 1 ) ) = 3
     * Therefore, 3x3 square
     *
     * <p/>Example 3 - {@code maxValue = 8}
     * √8 ≈ 2.828427 + 1 = 3.828427 rounded down = 3
     * ( 3 += ( 3 + 1 = 4 % 2 = 0 ) ) = 3
     * Therefore, 3x3 square
     *
     * <p/>Example 4 - {@code maxValue = 9}
     * √9 = 3 + 1 = 4 rounded down = 4
     * ( 4 += ( 4 + 1 = 5 % 2 = 1 ) ) = 5
     * Therefore, 5x5 square
     *
     * @param maxValue the last value in the spiral
     * @return
     */
    static Integer[][] getMatrixFromMaxValue( int maxValue ) {
        int squareSize = 1

        if ( maxValue > 0 ) {
            // take the square root, add one, round down
            squareSize = Math.floor( Math.sqrt( maxValue ) + 1 )
            // current value plus one, divide by two, add the remainder back
            squareSize += ( ( squareSize + 1 ) % 2 )
        }

        new Integer[ squareSize ][ squareSize ].collect { new Integer[ squareSize ] }
    }

    /**
     * Construct a spiral of given length in the matrix provided from outside in. Places not consumed by the spiral are
     * filled with {@code null}
     *
     * @param matrix the square array of arrays which will contain the spiral. Assumed to be large enough to hold the
     * requested spiral rendering
     * @param maxValue the number of cells in length the spiral will consume within the matrix. I.E. - the size of the
     * spiral
     */
    static void buildSpiralInMatrix( Integer[][] matrix, int maxValue ) {
        int x, xCount, xBound, y, yCount, yBound
        // Start right and move left
        x = xBound = matrix.length - 1
        // Only one (the first) side will ever have matrix.length fields
        yBound = xBound - 1
        // Start down and move up
        y = xCount = yCount = 0

        Direction[] directions = Direction.values()
        Direction currentDirection = LEFT

        for ( int value = matrix.length ** 2 - 1; value >= 0; value-- ) {
            // for the majority of cases where the matrix required is larger than spiral length, insert null as filler
            matrix[ y ][ x ] = value > maxValue ? null : value

            switch ( currentDirection ) {
                case LEFT:
                case RIGHT:
                    // when you have reached the edge of the matrix (x == 0 || x == matrix.length - 1)
                    // when you have reached a column that contains a previously calculated portion of the spiral
                    if ( ++xCount > xBound ) {
                        // traverse one less column on each horizontal pass
                        xBound--
                        // reset the counter
                        xCount = 0
                        // ensure positive array index by adding length before decrement and modulo
                        currentDirection = directions[ ( currentDirection.ordinal() + directions.length - 1 ) % directions.length ]
                    }
                    break
                case DOWN:
                case UP:
                    // when you have reached the edge of the matrix (y == 0 || y == matrix.length - 1)
                    // when you have reached a row that contains a previously calculated portion of the spiral
                    if ( ++yCount > yBound ) {
                        // traverse one less row on each vertical pass
                        yBound--
                        yCount = 0
                        currentDirection = directions[ ( currentDirection.ordinal() + directions.length - 1 ) % directions.length ]
                    }
                    break
            }

            // increment or decrement your position based on your direction
            switch ( currentDirection ) {
                case LEFT: x--; break
                case DOWN: y--; break
                case RIGHT: x++; break
                case UP: y++; break
            }
        }

        matrix
    }

    /**
     * Pretty print the spiral stored in the matrix
     *
     * @param matrix the pre-populated spiral matrix
     * @return the ASCII representing the numeric spiral
     */
    static String getAsciiFromMatrix( Integer[][] matrix ) {
        // calculate the width of the largest number in the spiral
        int charWidth = ( matrix.length ** 2 ).toString().length()

        // for every row of ints
        matrix.collect { Integer[] row ->
            // for every int in the row
            row.collect {
                // fill in whitespace for null ints, offset any others into nice columns
                it == null ? ' ' * charWidth : ' ' * ( charWidth - it.toString().length() ) + it.toString()
            }.join( ' ' ) // join ints with space
        }.join( '\n' ) // join rows with newline
    }
}
