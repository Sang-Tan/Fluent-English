export enum MediaType {
  IMAGE = "image",
  VIDEO = "video",
  AUDIO = "audio",
  RAW = "raw",
}

export interface FileResource {
  mediaType?: MediaType;
  type: string;
  url: string;
}

export interface CloudinaryResource extends FileResource {
  type: "cloudinary";
  publicId: string;
}

export interface ExternalResource extends FileResource {
  type: "ext";
}
