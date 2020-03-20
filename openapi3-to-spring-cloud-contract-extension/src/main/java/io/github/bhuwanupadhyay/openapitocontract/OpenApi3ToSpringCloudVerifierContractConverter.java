package io.github.bhuwanupadhyay.openapitocontract;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.parser.OpenAPIV3Parser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.contract.spec.Contract;
import org.springframework.cloud.contract.spec.ContractConverter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class OpenApi3ToSpringCloudVerifierContractConverter implements ContractConverter<Collection<PathItem>> {

    @Override
    public boolean isAccepted(File file) {
        try {
            OpenAPI spec = new OpenAPIV3Parser().read(file.getPath());

            Optional.ofNullable(spec)
                    .filter(openAPI -> openAPI.getPaths().size() > 0)
                    .map(OpenAPI::getPaths)
                    .map(Paths::getExtensions)
                    .orElseThrow(() -> new RuntimeException("Invalid OpenAPI Specification"));

            var contractsFound = false;
            //check spec for contracts
            for (Entry<String, PathItem> entry : spec.getPaths().entrySet()) {
                PathItem v = entry.getValue();
                if (!contractsFound) {
                    for (Operation operation : v.readOperations()) {
                        if (Objects.nonNull(operation.getExtensions())) {
                            Collection<Object> contracts = (Collection<Object>) operation.getExtensions().get("x-contracts");
                            if (contracts != null && contracts.size() > 0) {
                                contractsFound = true;
                            }
                        }
                    }
                }
            }
            return contractsFound;
        } catch (Exception e) {
            log.error("Unexpected error in reading contract file");
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public Collection<Contract> convertFrom(File file) {
        Collection<Contract> sccContracts = new ArrayList<>();

        var spec = new OpenAPIV3Parser().read(file.getPath());

        return sccContracts;
    }

    @Override
    public Collection<PathItem> convertTo(Collection<Contract> contract) {
        throw new RuntimeException("Not Implemented");
    }

}
