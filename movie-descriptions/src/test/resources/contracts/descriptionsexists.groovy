import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "movie description by id - successful"
    request {
        method GET()
        url "/descriptions/42"
    }

    response {
        status 200
        body(
                description: $(regex('.*'))
        )
        headers {
            contentType(applicationJson())
        }
    }
}
