Sobel Edge Detector
===================

* Read an image file (PNG format file will be supplied)

* Separate the image into three components (red-green-blue)

* Perform an edged detection operation on the green component using the Sobel gradient operator

* Use this formula to scale your magnitude result prior to writing to a file
 - mag'[i][j] = ( mag[i][j] * 255 ) / (sqrt(pow2(1024) + pow2(1024)))

* Use this formula to scale your direction result prior to writing to a file
 - dir'[i][j] = (( dir[i][j] + Pi) / (2 * Pi) ) * 255

* Write the resultant image to an image file (BMP, PNG, other “standard” uncompressed format)
