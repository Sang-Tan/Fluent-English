import { FileResource, CloudinaryResource, ExternalResource } from "./types";

export function isCloudinaryResource(
  resource: FileResource
): resource is CloudinaryResource {
  return resource.type === "cloudinary";
}

export function isExternalResource(
  resource: FileResource
): resource is ExternalResource {
  return resource.type === "external";
}
