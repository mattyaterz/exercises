package com.github.yaterz.ascii.spiral.test

import com.github.yaterz.ascii.spiral.exception.InvalidInputException
import com.github.yaterz.ascii.spiral.main.AsciiSpiral
import org.junit.FixMethodOrder
import org.junit.Test;

/**
 * SpiralTester
 *
 * @author mattyaterz
 * @since 2014-06-17
 */
@FixMethodOrder class SpiralTester {
    @Test( expected = InvalidInputException ) void nullArgs() {
        AsciiSpiral.main( null )
    }

    @Test( expected = InvalidInputException ) void noArgs() {
        AsciiSpiral.main( [] as String[] )
    }

    @Test( expected = InvalidInputException ) void tooManyArgs() {
        AsciiSpiral.main( 'a'..'b' as String[] )
    }

    @Test( expected = InvalidInputException ) void nonIntegerArg() {
        AsciiSpiral.main( [ 'shouldFail' ] as String[] )
    }

    @Test( expected = InvalidInputException ) void negativeLength() {
        AsciiSpiral.main( [ -1 ] as String[] )
    }

    @Test void fortyEightLength() {
        AsciiSpiral.main( [ "48" ] as String[] )
    }

    @Test void oneByOneMaxValueZero() {
        assert [ [ null ] ] == AsciiSpiral.getMatrixFromMaxValue( 0 )
    }

    @Test void threeByThreeMaxValueOne() {
        assert [ [ ], [ ], [ ] ].collect { new Integer[ 3 ] } == AsciiSpiral.getMatrixFromMaxValue( 1 )
    }

    @Test void threeByThreeMaxValueEight() {
        assert [ [ ], [ ], [ ] ].collect { new Integer[ 3 ] } == AsciiSpiral.getMatrixFromMaxValue( 8 )
    }

    @Test void fiveByFiveMaxValueNine() {
        assert [ [ ], [ ], [ ], [ ], [ ] ].collect { new Integer[ 5 ] } == AsciiSpiral.getMatrixFromMaxValue( 9 )
    }

    @Test void fiveByFiveMaxValueTwentyFour() {
        assert [ [ ], [ ], [ ], [ ], [ ] ].collect { new Integer[ 5 ] } == AsciiSpiral.getMatrixFromMaxValue( 24 )
    }

    @Test void sevenBySevenMaxValueTwentyFive() {
        assert [ [ ], [ ], [ ], [ ], [ ], [ ], [ ] ].collect { new Integer[ 7 ] } == AsciiSpiral.getMatrixFromMaxValue( 25 )
    }

    @Test void build1x1maxValue0() {
        Integer[][] matrix = [ new Integer[1] ]
        AsciiSpiral.buildSpiralInMatrix( matrix, 0 )
        assert [ [ 0 ] ] as Integer[][] == matrix
    }

    @Test void build3x3maxValue1() {
        Integer[][] matrix = [ [ ], [ ], [ ] ].collect { new Integer[ 3 ] }
        AsciiSpiral.buildSpiralInMatrix( matrix, 1 )
        assert [
            [ null, null, null ],
            [ null,    0,    1 ],
            [ null, null, null ],
        ] as Integer[][] == matrix
    }

    @Test void build3x3maxValue8() {
        Integer[][] matrix = [ [ ], [ ], [ ] ].collect { new Integer[ 3 ] }
        AsciiSpiral.buildSpiralInMatrix( matrix, 8 )
        assert [
            [ 6, 7, 8 ],
            [ 5, 0, 1 ],
            [ 4, 3, 2 ],
        ] as Integer[][] == matrix
    }

    @Test void build5x5maxValue9() {
        Integer[][] matrix = [ [ ], [ ], [ ], [ ], [ ] ].collect { new Integer[ 5 ] }
        AsciiSpiral.buildSpiralInMatrix( matrix, 9 )
        assert [
            [ null, null, null, null, null ],
            [ null,    6,    7,    8,    9 ],
            [ null,    5,    0,    1, null ],
            [ null,    4,    3,    2, null ],
            [ null, null, null, null, null ]
        ] as Integer[][] == matrix
    }

    @Test void build5x5maxValue24() {
        Integer[][] matrix = [ [ ], [ ], [ ], [ ], [ ] ].collect { new Integer[ 5 ] }
        AsciiSpiral.buildSpiralInMatrix( matrix, 24 )
        assert [
            [ 20, 21, 22, 23, 24 ],
            [ 19,  6,  7,  8,  9 ],
            [ 18,  5,  0,  1, 10 ],
            [ 17,  4,  3,  2, 11 ],
            [ 16, 15, 14, 13, 12 ]
        ] as Integer[][] == matrix
    }

    @Test void build7x7maxValue25() {
        Integer[][] matrix = [ [ ], [ ], [ ], [ ], [ ], [ ], [ ] ].collect { new Integer[ 7 ] }
        AsciiSpiral.buildSpiralInMatrix( matrix, 25 )
        assert [
            [ null, null, null, null, null, null, null ],
            [ null,   20,   21,   22,   23,   24,   25 ],
            [ null,   19,    6,    7,    8,    9, null ],
            [ null,   18,    5,    0,    1,   10, null ],
            [ null,   17,    4,    3,    2,   11, null ],
            [ null,   16,   15,   14,   13,   12, null ],
            [ null, null, null, null, null, null, null ]
        ] as Integer[][] == matrix
    }
}
