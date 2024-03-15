import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "movie description by id - not found"
    request {
        method GET()
        url "/descriptions/43"
    }

    response {
        status 404
    }
}
