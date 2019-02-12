#Parking REST API

REST service for City Parking

Operator endpoint:

    - check all list of parking: http://(server)/admin
    - check parked car status: http://(server)/admin/{vehiclePlateNumber}
    - get amount earned from given day: http://(server)/admin/{year}/{month}/{day}

Driver endpoin:

    - park car: http://(server)/park?vehiclePlatenumber={vehiclePlateNumber}&startDateTime={startDateTime}&endDateTime={endDateTime}&driverType={driverType}
    - check amount for parking: http://(server)/park/{vehiclePlateNumber}

