package backend.api.meetings;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

public interface MeetingsApi {

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Returns all meetings information.", description = "Returns all meetings information in a list.", tags={ "Meeting" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request completed.", content = @Content(schema = @Schema(implementation = MeetingPaginatedResponse.class))) })
    @RequestMapping(value = "/meetings",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<MeetingPaginatedResponse> getMeetings(@ApiParam(value = "the number of the page") @Valid @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber,
                                                       @ApiParam(value = "the number of element per page") @Valid @RequestParam(value = "size", required = false, defaultValue = "25") Integer size);

    @Operation(summary = "Returns one meeting information.", description = "Returns one meeting information from the given ID.", tags={ "Meeting" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request completed.", content = @Content(schema = @Schema(implementation = MeetingResponse.class))) })
    @RequestMapping(value = "/meetings/{meetingId}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<MeetingResponse> getMeeting(@Parameter(in = ParameterIn.PATH, description = "Meeting's Id", required = true, schema = @Schema()) @PathVariable("meetingId") Long meetingId);

    @Operation(summary = "Returns one meeting word cloud.", description = "Returns one meeting word cloud from the given ID.", tags={ "Meeting" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request completed.", content = @Content(schema = @Schema(implementation = MeetingWordCloudResponse.class))) })
    @RequestMapping(value = "/meetings/{meetingId}/wordcloud",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<MeetingWordCloudResponse> getWordCloudById(@Parameter(in = ParameterIn.PATH, description = "Meeting's Id", required = true, schema = @Schema()) @PathVariable("meetingId") Long meetingId);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request completed.", content = @Content(schema = @Schema(implementation = MeetingWordCloudResponse.class))) })
    @RequestMapping(value = "/meetings/{meetingId}/files/{fileId}",
            method = RequestMethod.GET)
    ResponseEntity<Resource> getFileById(@Parameter(in = ParameterIn.PATH, description = "Meeting's Id", required = true, schema = @Schema()) @PathVariable("meetingId") Long meetingId,
                                         @Parameter(in = ParameterIn.PATH, description = "File's Id", required = true, schema = @Schema()) @PathVariable("fileId") Long fileId);

    @Operation(summary = "Returns meetings information", description = "Returns meetings information filtered by matter.", tags={ "Meeting" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request completed.", content = @Content(schema = @Schema(implementation = MeetingPaginatedResponse.class))),
            @ApiResponse(responseCode = "403", description = "You are not allowed for use this method.") })
    @RequestMapping(value = "/meetings/findbymatter",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<MeetingPaginatedResponse> getMeetingsByMatter(
            @ApiParam(value = "the matter for filtering") @Valid @RequestParam(value = "matter", required = false, defaultValue = "") String matter,
            @ApiParam(value = "the number of the page") @Valid @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber,
            @ApiParam(value = "the number of elements per page") @Valid @RequestParam(value = "size", required = false, defaultValue = "25") Integer pageSize);

    @Operation(summary = "Returns all meetings information.", description = "Returns all meetings information in a list.", tags={ "Meeting" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request completed.", content = @Content(schema = @Schema(implementation = Void.class))) })
    @RequestMapping(value = "/meetings",
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<Void> registerMeeting(@ApiParam(value = "the new meeting") @Valid @RequestBody MeetingRegistrationRequest meetingRegistrationRequest);

    @RequestMapping(value = "/meetings/{meetingId}/files", method = RequestMethod.POST)
    ResponseEntity<MeetingFileResponse> addFile(
            @Parameter(in = ParameterIn.PATH, description = "Meeting's ID", required=true, schema=@Schema()) @PathVariable("meetingId") Long meetingId,
            @RequestParam("file") MultipartFile file);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request completed.", content = @Content(schema = @Schema(implementation = MeetingWordCloudResponse.class))) })
    @RequestMapping(value = "/meetings/{meetingId}/files/{fileId}",
            method = RequestMethod.POST)
    ResponseEntity<MeetingResponse> addMeetingDescriptionFromFile(
            @Parameter(in = ParameterIn.PATH, description = "Meeting's Id", required = true, schema = @Schema()) @PathVariable("meetingId") Long meetingId,
            @Parameter(in = ParameterIn.PATH, description = "File's Id", required = true, schema = @Schema()) @PathVariable("fileId") Long fileId);


    @Operation(summary = "Modify meeting's date", description = "Modify meeting's date", tags = { "Meeting" })
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meeting's date modified successfully"),
            @ApiResponse(responseCode = "404", description = "Meeting not found with the given ID")})
    @RequestMapping(value = "/meetings/{meetingId}/date",
            consumes = { "application/json" },
            method = RequestMethod.PUT)
    ResponseEntity<Void> modifyDate(@Parameter(in = ParameterIn.PATH, description = "Meeting's Id", required = true, schema = @Schema()) @PathVariable("meetingId") Long meetingId, @ApiParam(value = "the new date") @Valid @RequestBody MeetingDateChangeRequest meetingDateChangeRequest);

    @Operation(summary = "Modify meeting's matter", description = "Modify meeting's matter", tags = { "Meeting" })
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meeting's date modified successfully"),
            @ApiResponse(responseCode = "404", description = "Meeting not found with the given ID")
    })
    @RequestMapping(value = "/meetings/{meetingId}/matter",
            consumes = { "application/json" },
            method = RequestMethod.PUT)
    ResponseEntity<Void> modifyMatter(@Parameter(in = ParameterIn.PATH, description = "Meeting's Id", required = true, schema = @Schema()) @PathVariable("meetingId") Long meetingId, @ApiParam(value = "the new date") @Valid @RequestBody MeetingMatterChangeRequest meetingMatterChangeRequest);

    @Operation(summary = "Modify meeting's description", description = "Modify meeting's description", tags = { "Meeting" })
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meeting's description modified successfully"),
            @ApiResponse(responseCode = "404", description = "Meeting not found with the given ID")
    })
    @RequestMapping(value = "/meetings/{meetingId}/description",
            consumes = { "application/json" },
            method = RequestMethod.PUT)
    ResponseEntity<Void> modifyDescription(@Parameter(in = ParameterIn.PATH, description = "Meeting's Id", required = true, schema = @Schema()) @PathVariable("meetingId") Long meetingId, @ApiParam(value = "the new description") @Valid @RequestBody MeetingDescriptionChangeRequest meetingDescriptionChangeRequest);

    @Operation(summary = "Adds one merchant to the meeting", description = "Adds one merchant to the given meeting", tags = { "Meeting" })
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meeting's merchant modified successfully"),
            @ApiResponse(responseCode = "404", description = "Meeting not found with the given ID")
    })
    @RequestMapping(value = "/meetings/{meetingId}/merchants",
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<Void> addMerchant(@Parameter(in = ParameterIn.PATH, description = "Meeting's Id", required = true, schema = @Schema()) @PathVariable("meetingId") Long meetingId, @ApiParam(value = "merchant ID") @Valid @RequestBody MeetingSubjectChangeRequest meetingSubjectChangeRequest);

    @Operation(summary = "Adds one merchant to the meeting", description = "Adds one merchant to the given meeting", tags = { "Meeting" })
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meeting's merchant modified successfully"),
            @ApiResponse(responseCode = "404", description = "Meeting not found with the given ID")
    })
    @RequestMapping(value = "/meetings/{meetingId}/clients",
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<Void> addClient(@Parameter(in = ParameterIn.PATH, description = "Meeting's Id", required = true, schema = @Schema()) @PathVariable("meetingId") Long meetingId, @ApiParam(value = "merchant ID") @Valid @RequestBody MeetingSubjectChangeRequest meetingSubjectChangeRequest);

    @Operation(summary = "Adds one keyword to the meeting", description = "Adds one keyword to the given meeting", tags = { "Meeting" })
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meeting's merchant modified successfully"),
            @ApiResponse(responseCode = "404", description = "Meeting not found with the given ID")
    })
    @RequestMapping(value = "/meetings/{meetingId}/keywords",
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<Void> addKeyword(@Parameter(in = ParameterIn.PATH, description = "Meeting's Id", required = true, schema = @Schema()) @PathVariable("meetingId") Long meetingId, @ApiParam(value = "merchant ID") @Valid @RequestBody MeetingKeywordChangeRequest meetingKeywordChangeRequest);

    @Operation(summary = "Deletes one meeting's merchant", description = "Deletes one meeting's merchant", tags = { "Meeting" })
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meeting's client modified successfully"),
            @ApiResponse(responseCode = "404", description = "Meeting not found with the given ID")
    })
    @RequestMapping(value = "/meetings/{meetingId}/merchants/{merchantId}",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteMerchant(@Parameter(in = ParameterIn.PATH, description = "Meeting's Id", required = true, schema = @Schema()) @PathVariable("meetingId") Long meetingId, @Parameter(in = ParameterIn.PATH, description = "Merchant's Id", required = true, schema = @Schema()) @PathVariable("merchantId") Long merchantId);

    @Operation(summary = "Deletes one meeting's client", description = "Deletes one meeting's client", tags = { "Meeting" })
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meeting's client modified successfully"),
            @ApiResponse(responseCode = "404", description = "Meeting not found with the given ID")
    })
    @RequestMapping(value = "/meetings/{meetingId}/clients/{clientId}",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteClient(@Parameter(in = ParameterIn.PATH, description = "Meeting's Id", required = true, schema = @Schema()) @PathVariable("meetingId") Long meetingId, @Parameter(in = ParameterIn.PATH, description = "Client's Id", required = true, schema = @Schema()) @PathVariable("clientId") Long clientId);

    @Operation(summary = "Deletes one meeting's client", description = "Deletes one meeting's client", tags = { "Meeting" })
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meeting's client modified successfully"),
            @ApiResponse(responseCode = "404", description = "Meeting not found with the given ID")
    })
    @RequestMapping(value = "/meetings/{meetingId}/keywords/{keyword}",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteKeyword(@Parameter(in = ParameterIn.PATH, description = "Meeting's Id", required = true, schema = @Schema()) @PathVariable("meetingId") Long meetingId, @Parameter(in = ParameterIn.PATH, description = "Keyword", required = true, schema = @Schema()) @PathVariable("keyword") String keyword);

    @Operation(summary = "Deletes a meeting", description = "Deletes a meeting of the given ID.", tags={ "Meeting" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meeting deleted successfully."),

            @ApiResponse(responseCode = "404", description = "Meeting not found with the given ID.") })
    @RequestMapping(value = "/meetings/{meetingId}",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteMeeting(@Parameter(in = ParameterIn.PATH, description = "Meeting's ID", required=true, schema=@Schema()) @PathVariable("meetingId") Long meetingId);

    @RequestMapping(value = "/meetings/{meetingId}/files/{fileId}", method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteFile(
            @Parameter(in = ParameterIn.PATH, description = "Meeting's ID", required=true, schema=@Schema()) @PathVariable("meetingId") Long meetingId,
            @Parameter(in = ParameterIn.PATH, description = "File's ID", required=true, schema=@Schema()) @PathVariable("fileId") Long fileId);

}
