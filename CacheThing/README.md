## Notes

Just the raw sort of a million doubles
216 with no parallelism
41 ms with parallelism


Moving the pair into a map costs maybe 15 ms


With pair created in a map on the stream:
average: 66, min: 46, max: 253

With pair created during construction:
average: 51, min: 42, max: 231


